# Prisma Cloud Security Scan Action

This action scans a Docker image for security vulnerabilities using Prisma Cloud scanner.

## Description

The action retrieves Prisma Cloud credentials from Azure Key Vault and performs a security scan on the specified Docker image using the [PaloAltoNetworks/prisma-cloud-scan](https://github.com/PaloAltoNetworks/prisma-cloud-scan) action.

## Inputs

| Parameter | Description | Required |
|-----------|-------------|----------|
| `az-keyvault-name` | Name of Azure Key Vault containing Prisma Cloud credentials | Yes |
| `az-secret-prisma-url` | Secret name for Prisma Cloud console URL | Yes |
| `az-secret-prisma-access-key-id` | Secret name for Prisma Cloud access key ID | Yes |
| `az-secret-prisma-access-key-secret` | Secret name for Prisma Cloud access key secret | Yes |
| `image-name` | Full path to Docker image to scan (including tag) | Yes |
| `jf-url` | JFrog Artifactory URL for pulling images (optional) | No |
| `jf-access-token` | JFrog Artifactory access token (optional) | No |

## Prerequisites

- Azure authentication should be set up before using this action
- The `retrieve-keyvault-secret.sh` script must be available in the PATH

## Usage Example

```yaml
- name: Prisma Cloud security scan
  id: security-scan
  uses: ./.github/actions/prisma-scan
  with:
    az-keyvault-name: my-key-vault
    az-secret-prisma-url: prisma-cloud-url
    az-secret-prisma-access-key-id: prisma-access-key-id
    az-secret-prisma-access-key-secret: prisma-access-key-secret
    image-name: myregistry/myimage:latest
```

With JFrog credentials (typically needed for CD workflows):
```yaml
- name: Prisma Cloud security scan
  id: security-scan
  uses: ./.github/actions/prisma-scan
  with:
    az-keyvault-name: my-key-vault
    az-secret-prisma-url: prisma-cloud-url
    az-secret-prisma-access-key-id: prisma-access-key-id
    az-secret-prisma-access-key-secret: prisma-access-key-secret
    image-name: myregistry/myimage:latest
    jf-url: https://jfrog.example.com
    jf-access-token: my-jfrog-token
```
