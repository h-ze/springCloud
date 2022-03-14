git checkout heze 切换到本地分支
git add .
git commit -m "操作"
git pull origin heze 从heze分支上往下拉代码
git push origin heze 上传到heze分支上
git log 查看日志记录
git branch 查看分支
git branch -D 删除某一个分支
git checkout -b 分支  创建分支并切换到该分支（）

设置个人token
git remote set-url origin https://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@github.com/h-ze/springCloud.git/

git checkout -b local origin/WUYINGYUN 创建分支并切换到该分支并拉取远端分支的代码


git merge 分支   merge某个分支到当前分支

如果一直弹出登录框可以将http改成ssh协议；这是你github上拉取项目时的SSH地址
git remote set-url origin git地址
查看远程仓库
git remote -v

有时候，进行了错误的提交，但是还没有push到远程分支，想要撤销本次提交，可以使用git reset –-soft/hard命令
git reset 版本号（至少6位）
git reset --hard   # 回到最新的一次提交(彻底回到某个版本)
git reset --soft 回退到某个版本，只回退了commit的信息，不会恢复到index file一级。如果还要提交，直接commit即可
git status 查看状态
git rm -r --cached 文件名   清除缓存的文件，一般用于清除已经commit的未被忽略的文件 删除远程上的文件

<<<<<<和================是本地的
================和>>>>>>>>> 是其他提交引入的内容

该代码段行开始<<<<<<和================这里之间：

<<<<<< HEAD 
some code snippet here

================

就是你本地已经拥有的东西 - 你可以说，因为HEAD指向你当前的分支或提交。开始================和>>>>>>>>> 的代码片段：

================

some code snippet here

adsf23423423423423 >>>>>>>>>

是其他提交所引入的内容，在本例中为adsf23423423423423。这是合并到HEAD中的提交的对象名称（或“散列”，“SHA1sum”等）。git中的所有对象，无论它们是提交（版本），blob（文件），tree（目录）还是标签都有这样的对象名称，它们根据其内容唯一标识它们。

现在可以决定是否要保留头文件的代码段或由adsf23423423423423提交引入的代码段。

如果从从未触摸过的代码中得到此合并冲突，请保留adsf23423423423423提交引入的代码。



git如果Unstaged changes after reset的问题
可以使用 
git stash
git stash drop


git merge –no-ff 可以保存你之前的分支历史。能够更好的查看 merge历史，以及branch 状态。

git merge 则不会显示 feature，只保留单条分支记录。



开发一个新功能或修复一个Bug，开发者应当总是从develop分支创建出一个feature分支
，分支的命名建议为 feature/jira_id，比如： feature/test-1000。功能开发完成后，
应当再次把develop分支合并入feature/jira_id，在本地做集成测试，确认后发起一个Merge Request，
基于Merge Request开展code-review，code-review通过后由被授权的开发人员批准合并请求把代码合并入develop主干分支。
为了确保提交日志能够被正确comment到JIRA任务单，git提交信息应该包含任务单编号，例如：

# 创建feature分支
$ git checkout -b feature/test-1000 develop
Switched to a new branch "feature/test-1000"
 
# 在 feature/jira_id 分支提交代码， 如果你希望这条提交记录可以被自动comment到对应的jira任务单，那么请在提交描述信息里包含 jira_id，这是一个可选项。
$ git commit -m "Resolves test-1000, 其他描述本次提交的简短文字...."
 
# 再次把最新的develop分支代码合并入feature/JIRA_ID，
$ git checkout develop && git pull
$ git checkout feature/test-1000 && git merge --no-ff develop
 
# 集成测试，在Gitlab的WebGUI上操作发起Merge Request
 
 
# code-review通过后，合并代码到develop分支
$ git checkout develop
Switched to branch 'develop'
$ git merge --no-ff feature/test-1000
Updating ea1b82a..05e9557
(Summary of changes)





修复一个生产环境中的BUG，开发者首先要确定生产环境中代码的版本号，然后总是从master分支的指定tag创建出一个hotfix分支，
分支的命名建议为 hotfix/jira_id，比如： hotfix/test-x。问题修复完成后发起一个Merge Request，
基于Merge Request开展code-review，code-review通过后由被授权的开发人员批准合并请求把代码合并入master主干分支，
创建新的tag，然后部署到生产环境，同时把hotfix/jira_id合并入develop分支。


# 创建hotfix分支
$ git checkout -b hotfix-1.2.1 master
Switched to a new branch "hotfix-1.2.1"
 
# 在 hotfix/jira_id 分支提交代码， 如果你希望这条提交记录可以被自动comment到对应的jira任务单，那么请在提交描述信息里包含 jira_id，这是一个可选项。
$ git commit -a -m "Fixes test-x, 其他描述本次提交的简短文字...."
[hotfix-1.2.1 41e61bb] .....
1 files changed, 1 insertions(+), 1 deletions(-)
 
# 在Gitlab的WebGUI上操作发起Merge Request
 
# code-review通过后，合并代码到master、develop分支
$ git checkout master
Switched to branch 'master'
$ git merge --no-ff hotfix-1.2.1
Merge made by recursive.
(Summary of changes)
$ git tag -a 1.2.1
 
$ git checkout develop
Switched to branch 'develop'
$ git merge --no-ff hotfix-1.2.1
Merge made by recursive.
(Summary of changes)



2.1  master 分支

master 是发布用途的主干分支， 生产环境中的代码应该总是和 master 分支中指定Tag的代码一致，只有 master 分支的代码可以被发布到生产环境。

master 应当被设置为受保护分支，任何人不允许直接提交代码到master分支，项目负责人也不例外。

以下两个场景，代码需要被合并到 master 分支：

    当 release 分支的代码达到稳定状态并且准备好发布到生产环境时，代码需要被合并到 master 分支，然后标记上对应的版本标签（tag）。
    当生产环境中的代码需要执行一个紧急修复，hotfix 分支的代码通过验证并且准备好发布到生产环境时， 代码需要被合并到 master 分支，然后标记上对应的版本标签（tag）。

master 分支必须永久保留，不可以被删除。
2.2 hotfix 分支

hotfix 分支是临时的工作分支， 仅用于紧急修复生产环境中代码，hotfix分支基于 master 分支的某个tag创建，开发完后需要合并到 master 和 develop 分支，同时在master上标记上对应的版本标签（tag）。

分支的命名规则为 hotfix/jira_id。

从 hotfix 分支合并代码到 master 分支必须先必须先创建 merge request，并且 code-review合格后，才能由被授权的开发人员批准合并请求和执行代码合并。

hotfix/jira_id 被合并到 master 和 develop 分支后，执行紧急修复任务的开发人员可以决定是否保留 hotfix/jira_id 分支。
2.3 release 分支

release分支是版本预发布工作分支， 从develop分支创建，创建时已经包含了发布所需要的所有功能，通常在这个分支上不再开发新功能，仅对这个预发布版本进行Bug修复，创建文档及其他与发布相关的工作。一切就绪并且准备好发布到生产环境时，代码需要被合并到 master 分支，然后标记上对应的版本标签（tag），同时把代码合并到 develop分支。

分支的命名规则为 release/version。

release应当被设置为受保护分支，任何人不允许直接提交代码到release分支，项目负责人也不例外。

变更release分支的代码，通过创建 feature/jira_id 分支来进行。

release/version 分支被合并到 master 和 develop 分支后， 项目负责人可以决定是否保留 release/version 分支。
2.4 develop 分支

develop 分支是开发主干分支， 初始的 develop分支从 master 分支创建，版本规划中的所有功能都开发完成，准备预发布时，从develop分支创建 release/version 分支。

develop 应当被设置为受保护分支，任何人不允许直接提交代码到master分支，项目负责人也不例外。

变更develop分支的代码，通过创建 feature/jira_id 分支来进行。

develop 分支必须永久保留，不可以被删除。
2.5 feature 分支

feature 分支是临时的工作分支，用于执行develop分支或release/version分支的代码变更，feature 分支基于最新的 develop 分支或release/version分支创建，开发完成后需要合并回创建它的分支去。

分支的命名规则为 feature/jira_id。

创建 merge request 请求把 feature/jira_id 分支合并到 develop分支或release/version分支之前，请务必把 develop分支或release/version分支的最新代码合并到feature/jira_id 分支，并确保代码能够正确运行。

从 feature/jira_id 分支合并代码到 develop 分支或release/version分支必须先创建 merge request，并且 code-review合格后，才能由被授权的开发人员批准合并请求和执行代码合并。

feature/jira_id 被合并到 develop分支或release/version分支后，执行该任务的开发人员可以决定是否保留 feature/jira_id 分支


出现 fatal: Will not add file alias 'cloud-GRPC/pom.xml' ('cloud-grpc/pom.xml' already exists in index)问题时可以使用
git rm -r --cached cloud-grpc/pom.xml

git stash

 git filter-branch --force --index-filter 'git rm --cached --ignore-unmatch E:/logs/springboot/web-warn-2022-03-11.0.log' --prune-empty --tag-name-filter cat -- --all

