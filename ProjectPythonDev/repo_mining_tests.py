import unittest
from datetime import datetime
from unittest.mock import patch
from repo_mining import repo_miner, mine_given_repos
import os


class TestRepoMining(unittest.TestCase):
    def test_repo_miner(self):
        test_start_date = datetime(2023, 9, 8, 00, 00, 00)
        test_end_date = datetime(2023, 9, 8, 23, 59, 59)
        test_repo = 'test-repo-1'

        commits = repo_miner(test_repo, test_start_date, test_end_date)

        self.assertEqual(len(commits), 5)
        self.assertEqual(commits[1]['num_inserts'], 1)
        self.assertEqual(commits[2]['num_deletes'], 1)
        self.assertEqual(commits[3]['num_files'], 1)

    def test_mine_given_repos_with_no_commits(self):
        number_weeks = 1

        sample_repos = ['eric-oss-adc-app-engineering']

        with patch('builtins.print') as mock_print:
            mine_given_repos(number_weeks, sample_repos)

        expected_msg = "No commits for repo eric-oss-adc-app-engineering in week 1"
        mock_print.assert_called_with(expected_msg)

    def test_mine_given_repos_modifies_file(self):
        number_weeks = 1
        target_repos = ['test-repo-1']
        wd = os.getcwd()

        file_path = os.path.join(wd, 'data', 'mining1.csv')

        mine_given_repos(number_weeks, target_repos)

        with open(file_path, 'r') as modified_file:
            modified_content = modified_file.read()

        self.assertNotEqual(None, modified_content)


if __name__ == '__main__':
    unittest.main()
