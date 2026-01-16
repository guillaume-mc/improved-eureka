# Checkout Deployment Repository Action

This GitHub Action allows you to checkout a deployment repository using GitHub App credentials stored in Azure Key Vault.

## Prerequisites

- Azure credentials configured in your GitHub repository or organization
- A GitHub App with appropriate permissions to access the target repository
- The GitHub App's ID and private key stored in an Azure Key Vault

## Inputs

| Name | Description | Required |
|------|-------------|----------|
| `deployment-repo-name` | Deployment repository name to checkout | Yes |
| `organization` | GitHub organization name | Yes |
| `az-keyvault-name` | Azure Key Vault name to retrieve secrets from | Yes |
| `az-secret-gh-app-id` | Secret name for GitHub App ID in Azure Key Vault | Yes |
| `az-secret-gh-app-private-key` | Secret name for GitHub App Private Key in Azure Key Vault | Yes |

## Outputs

| Name | Description |
|------|-------------|
| `checkout-path` | Path where the repository was checked out |

## Usage

```yaml
name: Example Workflow

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    permissions:
      id-token: write  # Required for OIDC authentication with Azure
      contents: read
    
    steps:
      - name: Checkout current repository
        uses: actions/checkout@v4
      
      - name: Checkout deployment repository
        uses: your-org/sparkpoc-ci-cd/actions/checkout-deployment-repo@main
        id: checkout-deployment
        with:
          deployment-repo-name: 'your-deployment-repo'
          organization: 'your-org'
          az-keyvault-name: 'your-keyvault'
          az-secret-gh-app-id: 'github-app-id-secret-name'
          az-secret-gh-app-private-key: 'github-app-private-key-secret-name'
      
      # Use the checked out repository
      - name: Use checked out repository
        run: |
          cd ${{ steps.checkout-deployment.outputs.checkout-path }}
          ls -la
```

## Note

This action requires Azure credentials to be available in the GitHub environment. These are typically provided through GitHub repository or organization secrets and used by the Azure Login step.

```yaml
env:
  AZURE_CLIENT_ID: ${{ secrets.AZURE_CLIENT_ID }}
  AZURE_TENANT_ID: ${{ secrets.AZURE_TENANT_ID }}
  AZURE_SUBSCRIPTION_ID: ${{ secrets.AZURE_SUBSCRIPTION_ID }}
```
