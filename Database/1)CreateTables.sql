-- Create a new database called 'Beanstalk'
-- Connect to the 'master' database to run this snippet
USE master
GO
-- Set the database to single user mode to close existing connections
ALTER DATABASE Beanstalk SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
GO
-- Drop the database if it exists
IF EXISTS (
    SELECT name
        FROM sys.databases
        WHERE name = N'Beanstalk'
)
DROP DATABASE Beanstalk;
GO

CREATE DATABASE Beanstalk
GO
USE Beanstalk
GO
CREATE TABLE Users(
    UserID INT IDENTITY(1,1) NOT NULL,
    UserName NVARCHAR(50) NOT NULL,
    OAuthUserName VARCHAR(50) NOT NULL,
    Auth0ID VARCHAR(50) NOT NULL,
);
ALTER TABLE Users
ADD CONSTRAINT PK_Users PRIMARY KEY (UserID);
GO


CREATE TABLE Chat(
    ChatID INT IDENTITY(1,1) NOT NULL,
    ChatUserID1 INT NOT NULL,
    ChatUserID2 INT NOT NULL,
);
ALTER TABLE Chat
ADD CONSTRAINT PK_Chat PRIMARY KEY (ChatID);
GO
ALTER TABLE Chat
ADD CONSTRAINT FK_Chat_User1 FOREIGN KEY (ChatUserID1) REFERENCES Users(UserID);
ALTER TABLE Chat
ADD CONSTRAINT FK_Chat_User2 FOREIGN KEY (ChatUserID2) REFERENCES Users(UserID);
GO


CREATE TABLE Messages(
    MessageID INT IDENTITY(1,1) NOT NULL,
    ChatID INT NOT NULL,
    MessageContent NVARCHAR(20) NOT NULL,
    MessageTimeStamp DATETIME NOT NULL,
    MessageSenderID INT NOT NULL,
);

ALTER TABLE Messages
ADD CONSTRAINT PK_Messages PRIMARY KEY (MessageID);
GO
ALTER TABLE Messages
ADD CONSTRAINT FK_Messages_Chat FOREIGN KEY (ChatID) REFERENCES Chat(ChatID);
ALTER TABLE Messages
ADD CONSTRAINT FK_Messages_Sender FOREIGN KEY (MessageSenderID) REFERENCES Users(UserID);

