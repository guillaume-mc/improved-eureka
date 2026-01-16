# Fetch Secret from Azure Keyvault GitHub Action

This custom GitHub Action fetches one or more secrets from an Azure Key Vault and outputs them as a JSON object. It is designed for use in composite workflows where secrets need to be securely retrieved and masked.

## Inputs

| Name              | Description                                                      | Required |
|-------------------|------------------------------------------------------------------|----------|
| az-keyvault-name  | Name of the Azure Key Vault to fetch secrets from                | true     |
| secret-keys       | Key-value pairs or list of secret names to fetch, one per line.  | true     |

- `secret-keys` can be provided as a multi-line string. Each line can be either:
  - `output_key=secret_key` (to map the output key to a different secret name)
  - `secret_key` (output key will be the same as the secret name)

## Outputs

| Name           | Description                        |
|----------------|------------------------------------|
| secret-values  | JSON object of fetched secrets      |

## Example Usage

```yaml
- name: Fetch secrets from Azure Key Vault
  uses: ./.github/actions/fetch-secrets
  with:
    az-keyvault-name: my-keyvault
    secret-keys: |
      db-password=DB_PASSWORD
      API_KEY
  id: fetch_secrets

- name: Use secrets
  run: |
    echo "DB_PASSWORD: ${{ fromJson(steps.fetch_secrets.outputs.secret-values)['db-password'] }}"
    echo "API_KEY: ${{ fromJson(steps.fetch_secrets.outputs.secret-values)['API_KEY'] }}"
```

## How It Works
- For each line in `secret-keys`, the action fetches the secret from the specified Azure Key Vault.
- If a line is in the form `output_key=secret_key`, the secret value is mapped to `output_key` in the output JSON.
- If only `secret_key` is provided, the output key is the same as the secret name.
- All secret values are masked in the workflow logs.

## Requirements
- The workflow must have access to Azure CLI (`az`) and be authenticated to the Azure subscription with access to the Key Vault.
- The `jq` utility must be available in the runner environment.

## Security
- All secrets are masked in the logs using GitHub Actions masking.
- Output is a single JSON object for easy consumption in subsequent steps.