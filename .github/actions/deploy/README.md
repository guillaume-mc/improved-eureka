# Deploy to ArgoCD Action

This GitHub Action retrieves credentials from Azure Key Vault and triggers a deployment using ArgoCD.

## Description

The action:
1. Retrieves ArgoCD credentials from Azure Key Vault
2. Logs into ArgoCD using the retrieved credentials
3. Creates or updates an application in ArgoCD
4. Triggers a sync operation to deploy the application
5. Optionally verifies the deployment status

## Inputs

| Name | Description | Required | Default |
|------|-------------|----------|---------|
| `az-keyvault-name` | Name of the Azure Key Vault | Yes | N/A |
| `az-secret-argocd-url` | Secret name for the ArgoCD URL | Yes | N/A |
| `az-secret-argocd-username` | Secret name for the ArgoCD username | Yes | N/A |
| `az-secret-argocd-password` | Secret name for the ArgoCD password | Yes | N/A |
| `repo-name` | Name of the repository | Yes | N/A |
| `deployment-repo-url` | URL of the deployment repository | Yes | N/A |
| `cluster` | Cluster path | Yes | N/A |
| `image-tag` | Image tag to deploy | Yes | N/A |
| `verify-deployment` | Whether to verify the deployment | No | `true` |

## Prerequisites

- Azure authentication should be set up before using this action
- The `retrieve-keyvault-secret.sh` script must be available in the PATH
- ArgoCD CLI must be installed in the runner environment

## Usage Example

```yaml
- name: Deploy application using ArgoCD
  uses: mastercardlabs/sparkpoc-ci-cd/.github/actions/deploy@main
  with:
    az-keyvault-name: my-key-vault
    az-secret-argocd-url: argocd-url-secret-name
    az-secret-argocd-username: argocd-username-secret-name
    az-secret-argocd-password: argocd-password-secret-name
    repo-name: my-application
    deployment-repo-url: https://github.com/myorg/deployment-repo
    cluster: aks/my-cluster/develop
    image-tag: abc123
    verify-deployment: true
```
