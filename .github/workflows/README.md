# GitHub Actions Workflows

## 🚀 Automated Builds

This repository uses GitHub Actions to automatically build APK files.

### Workflows

#### 1. Build APK (Automatic)
- **Triggers**: Push to main/master branch
- **Output**: Debug APK
- **Retention**: 30 days
- **Download**: Actions → Workflows → Build APK → Artifacts

#### 2. Release APK (Manual)
- **Triggers**: Manual dispatch
- **Output**: Signed Release APK
- **Retention**: 90 days
- **Requires**: Keystore secrets setup

### How to Download APK

1. Go to: https://github.com/zesbe/ZESBE-Chat/actions
2. Click on the latest workflow run
3. Scroll to "Artifacts" section
4. Download `ZESBE-Chat-Debug-APK`

### Setup for Signed Release

To create signed release APKs:

1. Generate keystore:
```bash
keytool -genkey -v -keystore zesbe-release.jks -keyalg RSA -keysize 2048 -validity 10000 -alias zesbe
```

2. Encode to base64:
```bash
base64 zesbe-release.jks > keystore.txt
```

3. Add secrets to repo (Settings → Secrets and variables → Actions):
   - `KEYSTORE_BASE64`: Content of keystore.txt
   - `KEYSTORE_PASSWORD`: Your keystore password
   - `KEY_ALIAS`: zesbe
   - `KEY_PASSWORD`: Your key password

4. Trigger: Actions → Release APK → Run workflow

### Build Status

[![Build APK](https://github.com/zesbe/ZESBE-Chat/actions/workflows/build.yml/badge.svg)](https://github.com/zesbe/ZESBE-Chat/actions/workflows/build.yml)

---

**Build Different.** ⚡
