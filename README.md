# Jourly App
- Mobile Application Development Project @ BTH
- Quick & easy mood tracking and journaling

## Contribution Workflow

Please follow the following workflow when contributing [GitHub Flow](https://docs.github.com/en/get-started/quickstart/github-flow):

- (Pick issue and assign it to yourself => usually this is done in our weekly meetings)

- Checkout repo (only first time):

`git checkout https://github.com/nagychris/jourly-app.git

- Create new feature branch based on `main` (***Note**: _branch-name_ should optimally adhere to this naming schema, to have a better overview: _yourUsername/githubIssueNumber-description_):

 `git checkout -b <branch-name> origin/main`

- Develop the feature by making reasonably-sized commits with meaningful messages (some [best practices](https://gist.github.com/luismts/495d982e8c5b1a0ced4a57cf3d93cf60) for Git commits) => Example: 

`git commit -m "add entry list component"`

- Make sure to frequently fetch the current changes made by others: 

`git fetch --all` and then `git merge origin/main`

- Push your commits when you finish your work, or when you want to have feedback: 

`git push origin HEAD` 

- Create a pull request with the feature branch in GitHub (usually a message is shown [here](https://github.com/nagychris/jourly-app/pulls) that automatically includes the recently pushed branch). The description should include a link to the issue that should be closed by the changes (e.g. "Closes #issue_number") and description of the changes (if required). Also post a link of the PR in Discord Channel "Development" to notify the others :

- Now, wait for comments (and if needed, apply the requested changes), and finally, for the approval of the PR 

- Branch may be merged into the `main` branch :clap:


