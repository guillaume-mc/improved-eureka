---
name: Git Workflow & Conventions
description: Git Workflow & Conventions
---

# Git Workflow & Conventions

> **Purpose**: This document provides explicit suggestions for the sequence of Git and related commands to run locally for safe, consistent, and auditable code changes. Only command suggestions are provided; no commands are executed automatically.

---

## 1) Role & Objectives

* Maintain a clean, linear, and well-documented Git history.
* Reduce merge conflicts and CI failures by syncing early and often.
* Enforce consistent branch names, commit messages, and PR quality.
* Protect secrets and production data; never exfiltrate or modify sensitive info.

---

## 2) Command Suggestions & Boundaries

* This document only suggests explicit commands to run for each workflow step.
* No commands are executed automatically; users must run commands themselves.
* Destructive or privileged commands are never suggested.
* No commands requiring external network access or credentials are suggested unless explicitly required by the workflow.

---

## 3) Repository Assumptions

* Default branch: `main` (or `develop` if explicitly stated by the repo).
* CI runs on PRs and on merges to default branch.
* Conventional Commits enforced.
* Linting & tests required to pass before merge.

---

## 4) Branching Conventions

Use **kebab-case** and, when available, prepend the ticket/issue id.

**Patterns**

* `feature/<ticket>-<short-description>`
* `fix/<ticket>-<short-description>`
* `chore/<ticket>-<short-description>`
* `hotfix/<ticket>-<short-description>`

**Examples**

* `feature/PROJ-123-login-page`
* `fix/BUG-45-null-pointer-on-save`

---

## 5) Commit Message Standard (Conventional Commits)

```
<type>(<optional scope>): <summary in imperative mood>

<body – what & why, not how>
<footer – breaking changes, closes #ID>
```

**Types**: `feat`, `fix`, `docs`, `style`, `refactor`, `perf`, `test`, `build`, `chore`, `ci`
**Example**: `feat(auth): add login endpoint with JWT support`

Rules:

* Keep summary ≤ 72 chars; body wraps at ~100 chars.
* Prefer small, atomic commits.
* Use `BREAKING CHANGE:` in footer if applicable.

---

## 6) Standard Workflow (Local Change → PR → Merge)


Below is the explicit sequence of commands to run for a typical workflow. For any commands or steps that require a summary or description (such as commit messages or PR descriptions), use the actual changes in the current git diff to generate a concise summary. This ensures that commit messages and PRs accurately reflect what was changed.

**How to generate summaries for commit messages and PRs:**
- Before committing, run `git diff` and review the changes.
- Summarize the main changes (files, functions, logic, bugfixes, refactors, etc.) in the commit message or PR description.
- For commit messages, use the Conventional Commit format and keep the summary ≤ 72 chars. The body should briefly explain what and why the changes were made, referencing the diff.
- For PRs, include a summary of the diff, why the changes were made, and any relevant context for reviewers.


### 6.1 Sync Base Branch

1. Checkout the main branch:
  ```bash
  git checkout main
  ```
2. Pull the latest changes:
  ```bash
  git pull --ff-only origin main
  ```
  *If repo uses develop:*
  ```bash
  git checkout develop
  git pull --ff-only origin develop
  ```

### 6.2 Create a Feature Branch

3. Create and switch to a new feature branch:
  ```bash
  git checkout -b feature/<ticket>-<short-description>
  ```

### 6.3 Make Changes & Commit Iteratively

4. Check status of changes:
  ```bash
  git status
  ```
5. Add files to staging:
  ```bash
  git add <files>
  ```
6. Commit changes:
  Review the output of `git diff` and summarize the changes in your commit message.
  ```bash
  git commit -m "<type>(<scope>): <summary>"
  # Example summary: fix(add): correct agent file path logic in add command
  # Example body: Updates the file path logic in src/commands/agent/add.ts to use the correct assets/agents directory. See git diff for details.
  ```

### 6.4 Keep Branch Up-To-Date (prefer rebase during development)

7. Fetch latest changes from origin:
  ```bash
  git fetch origin
  ```
8. Rebase your branch on top of main (or develop):
  ```bash
  git rebase origin/main     # or origin/develop
  ```
  *If conflicts occur, resolve them and continue:*
  ```bash
  git rebase --continue
  ```

> During active development, prefer **rebase** to keep history linear. At merge time, follow the repo’s merge strategy (see §8).

### 6.5 Local Quality Gates

9. Run linters, formatters, type-checkers, and tests before pushing:
  ```bash
  # JavaScript/TypeScript
  npm ci || npm install
  npm run lint
  npm run build || tsc --noEmit
  npm test -- --watch=false

  # Python
  python -m pip install -r requirements.txt 2>/dev/null || true
  black --check . || black .
  flake8 || true
  pytest -q
  ```
  *Skip unavailable tools, but do not fail the workflow solely due to their absence.*

### 6.6 Push Branch

10. Push your branch to origin:
   ```bash
   git push -u origin feature/<ticket>-<short-description>
   ```

### 6.7 Open a Pull Request (PR)

11. Open a PR in your Git hosting service (GitHub, GitLab, etc.)
  *Title*: Use Conventional Commit style summarizing the main change, based on the current git diff.
  *Description*: Summarize the changes from the git diff, explain why they were made, how to test, links, risk/rollback plan, and screenshots for UI changes.
  *Checklist*: See §9.

### 6.8 Code Review & CI

12. Ensure ≥1–2 approvals (per repo policy).
13. Confirm all CI checks pass (lint, tests, build).
14. Address review comments via follow-up commits or amend + rebase.

### 6.9 Merge Strategy (per repo policy)

15. Merge using the repo’s preferred strategy:
   * Squash & merge (default): clean, single commit per PR
   * Rebase & merge: linear history, preserves individual commits
   * Merge commit: preserves topology for multi-commit PRs

### 6.10 Post-Merge Cleanup

16. Switch to main and update:
   ```bash
   git checkout main
   git pull --ff-only origin main
   ```
17. Delete your feature branch locally and remotely:
   ```bash
   git branch -d feature/<ticket>-<short-description>
   git push origin --delete feature/<ticket>-<short-description> || true
   ```

### 6.11 Optional: Tag Releases

18. Tag a release (if needed):
   ```bash
   git tag -a vX.Y.Z -m "Release X.Y.Z"
   git push origin vX.Y.Z
   ```

---

## 7) Safety, Secrets & Data Handling

* Never commit secrets (API keys, tokens, passwords). Use `.gitignore` and secret scanners if available.
* Treat `.env` files as sensitive; do not commit. Provide `.env.example` instead.
* Redact sensitive output from logs/PRs.
* Avoid destructive migrations without explicit approval and a rollback plan.

---

## 8) PR Template (Drop into `.github/pull_request_template.md`)

```
## Summary
- What & why:

## How to Test
- Steps:
- Expected:

## Risk / Rollback
- Risk level:
- Rollback plan:

## Links
- Ticket(s):
- Design/Spec:

## Checklist
- [ ] Branch up-to-date with base
- [ ] Lint passes locally
- [ ] Tests pass locally
- [ ] Added/updated docs
- [ ] Added/updated tests
- [ ] No secrets committed
- [ ] Screenshots for UI changes
```

---

## 9) Agent Checklist (Pre-Push & Pre-Merge)

**Pre-Push**

* [ ] Rebased on latest `main`/`develop`
* [ ] Lint & tests pass locally
* [ ] Commit messages follow Conventional Commits
* [ ] No large/binary files unintentionally added
* [ ] No secrets or credentials present

**Pre-Merge**

* [ ] CI green
* [ ] At least 1–2 approvals
* [ ] PR description complete (summary, test steps, risk, links)
* [ ] Docs/tests updated as needed

---

## 10) Useful Command Suggestions

Below are useful commands to run for common Git operations:

* See what changed:
  ```bash
  git status
  git diff
  ```
* Interactive add (optional):
  ```bash
  git add -p
  ```
* View concise log:
  ```bash
  git log --oneline --graph --decorate -n 20
  ```
* Abort a bad rebase/merge:
  ```bash
  git rebase --abort
  git merge --abort
  ```
* Discard local changes to a file (use with care):
  ```bash
  git checkout -- <path>
  ```
* Restore file from base branch (use with care):
  ```bash
  git checkout origin/main -- <path>
  ```
* Create a fixup commit against HEAD~N:
  ```bash
  git commit --fixup <commit-sha>
  ```
* Autosquash fixups during rebase:
  ```bash
  git rebase -i --autosquash origin/main
  ```

---

## 11) Conflict Resolution Protocol

1. Run `git status` and identify conflicted files.
2. Open each file; search for conflict markers `<<<<<<<`, `=======`, `>>>>>>>`.
3. Resolve manually or with a merge tool; prefer the version consistent with current system behavior and tests.
4. Run formatter & tests; ensure green.
5. `git add` resolved files, then `git rebase --continue` (or `git commit` for merges).

---

## 12) CI/CD Hooks (optional but recommended)

* Pre-commit hooks: format, lint, basic static analysis.
* Pre-push hooks: run unit tests for changed packages.
* PR checks: full test matrix, security scan, coverage threshold.

---

## 13) Troubleshooting Quick Tips

* **Detached HEAD**: `git switch -` or `git switch -c temp` then continue.
* **Accidental commit to main**: create a branch from the commit, revert on main, open PR.
* **Reset local branch to remote** (discard local changes):

  ```bash
  git fetch origin
  git reset --hard origin/<branch>
  ```
* **Amend last commit message**:

  ```bash
  git commit --amend
  ```

---

## 14) Governance

* This file defines the expected behavior for contributors and automation agents.
* Changes to this policy require PR review by maintainers.

