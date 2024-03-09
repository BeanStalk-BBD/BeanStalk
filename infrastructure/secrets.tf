
data "aws_secretsmanager_secret_version" "creds" {
  secret_id = "rds!db-2e07bdd7-f4f2-44ba-aa00-a53ce6c9db2b"
}