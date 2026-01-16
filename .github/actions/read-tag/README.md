# Read Deployment Image Tag

This action reads the deployment image tag from the env.properties file in a deployment repository.

## Inputs

| Name | Description | Required |
|------|-------------|----------|
| `env-properties-path` | Full path to the env.properties file | Yes |

## Outputs

| Name | Description |
|------|-------------|
| `tag` | The image tag read from the env.properties file |

## Example usage

```yaml
- name: Checkout Deployment Repository
  id: checkout-deployment-repo
  uses: mastercardlabs/sparkpoc-ci-cd/.github/actions/checkout-deployment-repo@main
  with:
    deployment-repo-name: ${{ needs.get-constants.outputs.deployment-repo-name }}
    organization: ${{ needs.get-constants.outputs.organization }}
    az-keyvault-name: ${{ needs.get-constants.outputs.az-keyvault-name }}
    az-secret-gh-app-id: ${{ needs.get-constants.outputs.az-secret-gh-app-id }}
    az-secret-gh-app-private-key: ${{ needs.get-constants.outputs.az-secret-gh-app-private-key }}

- name: Read Deployment Image Tag
  id: read-tag
  uses: ./.github/actions/read-tag
  with:
    env-properties-path: ${{ steps.checkout-deployment-repo.outputs.checkout-path }}/manifests/${{ needs.get-cluster.outputs.cluster }}/${{ github.event.repository.name }}/env.properties
```
