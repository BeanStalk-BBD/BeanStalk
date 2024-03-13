ALTER TABLE Users
DROP COLUMN OAuthUserName,
Auth0ID;
GO

CREATE PROCEDURE GetOrCreateUserSP
    @UserName NVARCHAR(50),
    @UserID INT OUTPUT
AS
BEGIN
    -- use a try-catch block to handle any errors
    BEGIN TRY
        
        SELECT @UserID = UserID FROM Users
        WHERE (UserName = @UserName)
        -- if no chat exists, create a new one and get the new chat id
        IF @UserID IS NULL
        BEGIN
            INSERT INTO Users (UserName)
            VALUES (@UserName)
            SET @UserID = SCOPE_IDENTITY()
        END
    END TRY
    BEGIN CATCH
        -- handle the error and return a negative value for @ChatID
        PRINT ERROR_MESSAGE()
        SET @UserID = -1
    END CATCH
END
GO