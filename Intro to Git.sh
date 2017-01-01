-----difference of files
diff -u old_file new_file #-u for showing the number of line

git log #history of changes

#difference between two commits
git diff commit_code_old commit_code_new 
git diff first_4_or_more_digit first_4_or_more digit

#colorful difference
git config --global color.ui auto

#check out prior commits
git checkout old_commit

#initialize a repository
git init

#add file to staging area
git add file_name

#to untrack files
git reset file_name

#difference between working directory and staging area
git diff 

#difference between staging area and repository
git diff --staged

#see all the branches
git branch

#create new branch
git branch new_branch_name

#switching between branched
git checkout branch_name

#merging new_branch into master branch
git checkout master
git merge master new_branch 

#show the difference between a commit and its parent
#especially useful after merging branched
git show commit_number

#delete branch
git branch -d branch_name

#solve conflict in merge
subl file_name
#solve all conflict parts
git status #check both modified
git add file_name 
git status #check all conflicts resolved 
git commmit #merge it!