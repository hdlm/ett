# Contributing Guide

Thank you for your interest in contributing to this project! 🚀  
We welcome contributions that help improve the app, fix bugs, and add new features.

---

## 📌 Branching Strategy

This repository uses the following branches:

- `main` → Production-ready code (used for releases)
- `dev` → Active development branch (integration of features)

✅ **All contributions must target the `dev` branch**

---

## 🔁 Contribution Workflow

Please follow these steps to contribute:

### 1. Fork the repository
Click the "Fork" button at the top of this repository.

### 2. Clone your fork
```bash

### 3. Checkout the development branch
```bash
git checkout dev

### 4. Create a feature branch
```bash
git checkout -b [feature|fix|refactor]/your-feature-name


### 5. Make your changes
```bash
git commit -m "feat: add user login functionality"

### 6. Push your branch
git push origin feature/your-feature-name

### 7. Open a Pull Request (PR)
- Target branch: dev
- Provide a clear description of your changes
- Link related issues if applicable

### Pull Request Requirements
- The app builds successfully
- Your code does not break existing features
- You tested your changes on a device or emulator
- Your code follows project conventions
- You have updated documentation if needed

### Keeping your Fork Updated
```bash
git remote add upstream https://github.com/ORIGINAL-OWNER/REPOSITORY-NAME.git
git fetch upstream
git checkout dev
git merge upstream/dev
``

### Reporting Issues
If you find a bug or want to suggest an improvement:

Go to the "Issues" tab
Search if it already exists
If not, create a new issue with:

Description
Steps to reproduce (if bug)
Screenshots (if applicable)

### Releases
Only maintainers are responsible for merging into main and handling releases.

Thanks for contributing! 🎉

