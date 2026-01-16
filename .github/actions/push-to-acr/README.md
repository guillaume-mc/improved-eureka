# Push to ACR Action

This GitHub Action handles pushing Docker images to Azure Container Registry (ACR).

## Features

- Retrieves ACR credentials from Azure Key Vault
- Logs in to Azure Container Registry
- Tags and pushes Docker images to ACR

## Usage

```yaml
- name: Push Docker Image to Azure Container Registry
  uses: ./.github/actions/push-to-acr
  with:
    az-keyvault-name: 'your-keyvault-name'
    az-secret-acr-url: 'secret-name-for-acr-url'
    az-secret-acr-username: 'secret-name-for-acr-username'
    az-secret-acr-password: 'secret-name-for-acr-password'
    image-source: 'source-path/image-name:tag'
    acr-registry-path: 'your-acr-path'
    repository-name: 'image-repository-name'
    image-tag: 'image-tag'
```

## Inputs

| Input | Description | Required |
|-------|-------------|----------|
| `az-keyvault-name` | Azure Key Vault name where secrets are stored | Yes |
| `az-secret-acr-url` | Secret name for ACR URL in Azure Key Vault | Yes |
| `az-secret-acr-username` | Secret name for ACR username in Azure Key Vault | Yes |
| `az-secret-acr-password` | Secret name for ACR password in Azure Key Vault | Yes |
| `image-source` | The full path of the source image including repository and tag | Yes |
| `acr-registry-path` | ACR registry path for tagging the image | Yes |
| `repository-name` | The repository name for the image | Yes |
| `image-tag` | The tag for the image | Yes |

## Prerequisites

This action requires:
- The `retrieve-keyvault-secret.sh` script to be available in the PATH
- Docker to be installed on the runner
- Azure login to be completed before this action runs
