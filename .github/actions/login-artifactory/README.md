# Login to Artifactory Action

This action retrieves credentials from Azure Key Vault and logs in to Artifactory Docker Registry.

## Inputs

- `az-keyvault-name`: Azure Key Vault name to retrieve secrets from
- `az-secret-artifactory-url`: Secret name for Artifactory URL in Azure Key Vault
- `az-secret-artifactory-username`: Secret name for Artifactory username in Azure Key Vault
- `az-secret-artifactory-token`: Secret name for Artifactory token in Azure Key Vault

## Usage Example

```yaml
- name: Login to Artifactory
  uses: ./.github/actions/login-artifactory
  with:
    az-keyvault-name: ${{ needs.get-constants.outputs.az-keyvault-name }}
    az-secret-artifactory-url: ${{ needs.get-constants.outputs.az-secret-artifactory-url }}
    az-secret-artifactory-username: ${{ needs.get-constants.outputs.az-secret-artifactory-user-name }}
    az-secret-artifactory-token: ${{ needs.get-constants.outputs.az-secret-artifactory-user-access-token }}
```
