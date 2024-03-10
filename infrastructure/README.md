##IF Planning failed. Terraform encountered an error while generating this plan.
    Then: aws configure sso
    -SSO session name [Dogs]: Temp
    -SSO start URL [None]:  https://bbd-internal-sso.awsapps.com/start#
    -SSO region [None]:  eu-west-1

##Using an S3 bucket to Terraform state
    -Why?
        Ensures that all team members working on the same project have access to the latest state information.
        Backup and Recovery: Storing your Terraform state in an S3 bucket provides durability and redundancy
        Terraform state file keeps track of the AWS resources you've created and all their settings.