import time
from datetime import datetime, timedelta
from pydriller import Repository
import os
import pandas as pd
from pydriller.metrics.process.hunks_count import HunksCount


def repo_miner(target_repo, start_date, end_date):
    commits = []
    contributors = {}
    cwd = os.getcwd()
    path = os.path.join(cwd, target_repo)

    repo = Repository(path, since=start_date, to=end_date)

    for commit in repo.traverse_commits():
        try:
            for mod_file in commit.modified_files:
                contributor = commit.author.name

                if contributor not in contributors:
                    contributors[contributor] = {'total': 0, 'files': set(), 'minor': False}

                if mod_file.source_code_before is not None:
                    lines = sum(1 for _ in mod_file.source_code_before)
                    change_ratio = mod_file.added_lines / lines

                    if change_ratio < 0.05:
                        contributors[contributor]['minor'] = True
                    else:
                        contributors[contributor]['minor'] = False

                contributors[contributor]['total'] += 1
                contributors[contributor]['files'].add(mod_file.filename)

            record = {
                'repo_name': target_repo,
                'commit_date': commit.committer_date,
                'num_inserts': commit.insertions,
                'num_deletes': commit.deletions,
                'num_files': commit.files,
                'change_set': len(commit.modified_files),
                'code_churn': commit.insertions - commit.deletions
            }

            commits.append(record)

        except ValueError:
            print('Could not read files for commit ' + commit.hash)
            continue

    metric = HunksCount(path, since=start_date, to=end_date)
    hunks_count = sum(metric.count().values())

    contributor_count = len(contributors)
    minor_contributors = sum(1 for contributor in contributors.values() if contributor['minor'] is True)
    contributor_names = '; '.join(contributors.keys())

    for rec in commits:
        rec['hunks_count'] = hunks_count
        rec['contributor_count'] = contributor_count
        rec['minor_contributors'] = minor_contributors
        rec['contributor_names'] = contributor_names

    return commits


def mine_given_repos(number_weeks, target_repos):
    for week_index in range(number_weeks):
        for target_repo in target_repos:

            today = datetime.today()
            end_date = today - timedelta(weeks=week_index)
            start_date = today - timedelta(weeks=(week_index + 1))
            start_time = time.time()
            target_repo_number = target_repos.index(target_repo) + 1
            empty_check = True

            commits = repo_miner(target_repo, start_date, end_date)

            if len(commits) == 0:
                print("No commits for repo {} in week {}".format(target_repo, week_index + 1))
                continue

            df_commits = pd.DataFrame(commits)

            if week_index == 0 or empty_check is True:
                df_commits.to_csv(f'data/mining{target_repo_number}.csv', index=False)
                empty_check = False
            else:
                df_commits.to_csv(f'data/mining{target_repo_number}.csv',
                                  mode='a', header=False, index=False)
                empty_check = False
            end_time = time.time()
            elapsed_time = end_time - start_time

            print("Finished repo {}, week {}, time taken: {} seconds."
                  .format(target_repo, week_index + 1, elapsed_time))


if __name__ == "__main__":
    target_repos = ['eric-oss-adc-app-engineering',
                    'eric-oss-app-mgr-app-engineering',
                    'eric-oss-dmm-app-engineering',
                    'eric-oss-eas-app-engineering',
                    'eric-oss-topology-handling-app-engineering']

    mine_given_repos(52, target_repos)
