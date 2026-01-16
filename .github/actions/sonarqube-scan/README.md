# SonarQube Scan GitHub Action

This action performs a SonarQube scan and quality gate check on a codebase.

## Inputs

### Required

- `az-keyvault-name`: Azure Key Vault name
- `az-secret-sonarqube-url`: Azure Key Vault secret name for SonarQube URL
- `az-secret-sonarqube-token`: Azure Key Vault secret name for SonarQube token
- `project-name`: SonarQube project name
- `scanner-version`: SonarQube scanner version

### Optional

- `scanner-binaries-url`: SonarQube scanner binaries URL
- `skip-quality-gate`: Skip SonarQube quality gate check (default: 'false')
- `java-binaries`: Path to Java binaries (default: 'target/classes')
- `java-test-binaries`: Path to Java test binaries (default: 'target/test-classes')
- `additional-args`: Additional SonarQube scanner arguments

## Outputs

- `quality-gate-status`: Status of the quality gate (PASSED or FAILED)

## Example Usage

```yaml
- name: SonarQube Analysis
  uses: ./.github/actions/sonarqube-scan
  with:
    az-keyvault-name: ${{ needs.get-constants.outputs.az-keyvault-name }}
    az-secret-sonarqube-url: ${{ needs.get-constants.outputs.az-secret-sonarqube-url }}
    az-secret-sonarqube-token: ${{ needs.get-constants.outputs.az-secret-sonarqube-user-access-token }}
    project-name: ${{ github.event.repository.name }}
    scanner-version: ${{ needs.get-constants.outputs.scanner-version }}
    scanner-binaries-url: ${{ needs.get-constants.outputs.scanner-binaries-url }}
```

## Dependencies

This action requires the `retrieve-keyvault-secret.sh` script to be available in the PATH.
