# Create Config Files Action

This action generates configuration files from templates for Java Maven projects.

## What it does

This action:
1. Creates a `maven-settings.xml` file from the template
2. Creates a `Dockerfile` from the template
3. Creates a `sonar-project.properties` file from the template and replaces the project name placeholder

## Inputs

| Input | Description | Required | Default |
|-------|-------------|----------|---------|
| `checkout-path` | Path to the checked out deployment repository containing templates | Yes | N/A |
| `project-name` | Name of the project to use in configuration files | Yes | `${{ github.event.repository.name }}` |

## Example usage

```yaml
- name: Checkout Deployment Repository
  id: checkout-deployment-repo
  uses: mastercardlabs/sparkpoc-ci-cd/.github/actions/checkout-deployment-repo@main
  # ... inputs for checkout deployment repo action

- name: Generate Configuration Files
  uses: ./.github/actions/create-config-files
  with:
    checkout-path: ${{ steps.checkout-deployment-repo.outputs.checkout-path }}
    project-name: ${{ github.event.repository.name }}
```

## Files Generated

1. `maven-settings.xml` - Maven settings configuration
2. `Dockerfile` - Docker build configuration 
3. `sonar-project.properties` - SonarQube analysis configuration
