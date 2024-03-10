
Drop TABLE Messages;
GO


CREATE TABLE Messages(
    MessageID BIGINT IDENTITY(1,1) NOT NULL,
    ChatID INT NOT NULL,
    MessageContent NVARCHAR(20) NOT NULL,
    MessageTimeStamp DATETIME NOT NULL,
    MessageSenderID INT NOT NULL,
);
GO

ALTER TABLE Messages
ADD CONSTRAINT PK_Messages PRIMARY KEY (MessageID);
GO
ALTER TABLE Messages
ADD CONSTRAINT FK_Messages_Chat FOREIGN KEY (ChatID) REFERENCES Chat(ChatID);
ALTER TABLE Messages
ADD CONSTRAINT FK_Messages_Sender FOREIGN KEY (MessageSenderID) REFERENCES Users(UserID);


ALTER TABLE Messages
ADD CONSTRAINT CK_Messages_NoWhitespace CHECK (MessageContent NOT LIKE '% %');
GO


CREATE FUNCTION CheckUserInChat(@UserID INT, @ChatID INT)
RETURNS INT
AS
BEGIN
    DECLARE @Result INT
    IF EXISTS (SELECT 1 FROM Chat WHERE ChatID = @ChatID AND (@UserID = ChatUserID1 OR @UserID = ChatUserID2))
        SET @Result = 1
    ELSE
        SET @Result = 0
    RETURN @Result
END
GO

ALTER TABLE Messages
ADD CONSTRAINT CK_Messages_SenderID
CHECK (dbo.CheckUserInChat(MessageSenderID, ChatID) = 1);
GO

CREATE PROCEDURE GetOrCreateChatSP
    @UserID1 INT,
    @UserID2 INT,
    @ChatID INT OUTPUT
AS
BEGIN
    -- use a try-catch block to handle any errors
    BEGIN TRY
        -- check if there is an existing chat between the two user ids
        SELECT @ChatID = ChatID FROM Chat
        WHERE (ChatUserID1 = @UserID1 AND ChatUserID2 = @UserID2)
        OR (ChatUserID1 = @UserID2 AND ChatUserID2 = @UserID1)
        -- if no chat exists, create a new one and get the new chat id
        IF @ChatID IS NULL
        BEGIN
            INSERT INTO Chat (ChatUserID1, ChatUserID2)
            VALUES (@UserID1, @UserID2)
            SET @ChatID = SCOPE_IDENTITY()
        END
    END TRY
    BEGIN CATCH
        -- handle the error and return a negative value for @ChatID
        PRINT ERROR_MESSAGE()
        SET @ChatID = -1
    END CATCH
END
GO

ALTER TABLE Chat
ADD CONSTRAINT CK_Chat_2Users CHECK (ChatUserID1 <> ChatUserID2);
GO


