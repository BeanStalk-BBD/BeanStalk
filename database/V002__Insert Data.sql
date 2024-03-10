
-- Insert some mock data into the Users table
INSERT INTO Users (UserName, OAuthUserName, Auth0ID)
VALUES
('Alice', 'AlICEEE34243', 'auth0|123456789'),
('Bob', 'BOBBY232', 'auth0|987654321'),
('Charlie', 'charlieDanger', 'auth0|456789123'),
('David', 'D8V!D', 'auth0|789123456'),
('Eve', 'GooDEVENING', 'auth0|123789456');
GO



-- Declare local variables for user IDs
DECLARE @AliceID INT;
DECLARE @BobID INT;
DECLARE @CharlieID INT;
DECLARE @DavidID INT;
DECLARE @EveID INT;

-- Assign user IDs from the Users table
SELECT @AliceID = UserID FROM Users WHERE UserName = 'Alice';
SELECT @BobID = UserID FROM Users WHERE UserName = 'Bob';
SELECT @CharlieID = UserID FROM Users WHERE UserName = 'Charlie';
SELECT @DavidID = UserID FROM Users WHERE UserName = 'David';
SELECT @EveID = UserID FROM Users WHERE UserName = 'Eve';

-- Insert some mock data into the Chat table
INSERT INTO Chat (ChatUserID1, ChatUserID2)
VALUES
(@AliceID, @BobID), -- Alice and Bob
(@AliceID, @CharlieID), -- Alice and Charlie
(@AliceID, @DavidID), -- Alice and David
(@AliceID, @EveID), -- Alice and Eve
(@BobID, @CharlieID), -- Bob and Charlie
(@BobID, @DavidID), -- Bob and David
(@BobID, @EveID), -- Bob and Eve
(@CharlieID, @DavidID), -- Charlie and David
(@CharlieID, @EveID), -- Charlie and Eve
(@DavidID, @EveID); -- David and Eve
GO
-- Declare local variables for user IDs
DECLARE @AliceID INT;
DECLARE @BobID INT;
DECLARE @CharlieID INT;
DECLARE @DavidID INT;
DECLARE @EveID INT;

-- Assign user IDs from the Users table
SELECT @AliceID = UserID FROM Users WHERE UserName = 'Alice';
SELECT @BobID = UserID FROM Users WHERE UserName = 'Bob';
SELECT @CharlieID = UserID FROM Users WHERE UserName = 'Charlie';
SELECT @DavidID = UserID FROM Users WHERE UserName = 'David';
SELECT @EveID = UserID FROM Users WHERE UserName = 'Eve';
-- Declare local variables for chat IDs
DECLARE @AliceBobChatID INT;
DECLARE @AliceCharlieChatID INT;
DECLARE @AliceDavidChatID INT;
DECLARE @AliceEveChatID INT;
DECLARE @BobCharlieChatID INT;
DECLARE @BobDavidChatID INT;
DECLARE @BobEveChatID INT;
DECLARE @CharlieDavidChatID INT;
DECLARE @CharlieEveChatID INT;
DECLARE @DavidEveChatID INT;

-- Assign chat IDs from the Chat table but make sure the person can be either user 1 or user 2

SELECT @AliceBobChatID = ChatID FROM Chat WHERE (ChatUserID1 = @AliceID AND ChatUserID2 = @BobID) OR (ChatUserID1 = @BobID AND ChatUserID2 = @AliceID);
SELECT @AliceCharlieChatID = ChatID FROM Chat WHERE (ChatUserID1 = @AliceID AND ChatUserID2 = @CharlieID) OR (ChatUserID1 = @CharlieID AND ChatUserID2 = @AliceID);
SELECT @AliceDavidChatID = ChatID FROM Chat WHERE (ChatUserID1 = @AliceID AND ChatUserID2 = @DavidID) OR (ChatUserID1 = @DavidID AND ChatUserID2 = @AliceID);
SELECT @AliceEveChatID = ChatID FROM Chat WHERE (ChatUserID1 = @AliceID AND ChatUserID2 = @EveID) OR (ChatUserID1 = @EveID AND ChatUserID2 = @AliceID);
SELECT @BobCharlieChatID = ChatID FROM Chat WHERE (ChatUserID1 = @BobID AND ChatUserID2 = @CharlieID) OR (ChatUserID1 = @CharlieID AND ChatUserID2 = @BobID);
SELECT @BobDavidChatID = ChatID FROM Chat WHERE (ChatUserID1 = @BobID AND ChatUserID2 = @DavidID) OR (ChatUserID1 = @DavidID AND ChatUserID2 = @BobID);
SELECT @BobEveChatID = ChatID FROM Chat WHERE (ChatUserID1 = @BobID AND ChatUserID2 = @EveID) OR (ChatUserID1 = @EveID AND ChatUserID2 = @BobID);
SELECT @CharlieDavidChatID = ChatID FROM Chat WHERE (ChatUserID1 = @CharlieID AND ChatUserID2 = @DavidID) OR (ChatUserID1 = @DavidID AND ChatUserID2 = @CharlieID);
SELECT @CharlieEveChatID = ChatID FROM Chat WHERE (ChatUserID1 = @CharlieID AND ChatUserID2 = @EveID) OR (ChatUserID1 = @EveID AND ChatUserID2 = @CharlieID);
SELECT @DavidEveChatID = ChatID FROM Chat WHERE (ChatUserID1 = @DavidID AND ChatUserID2 = @EveID) OR (ChatUserID1 = @EveID AND ChatUserID2 = @DavidID);

-- Insert some mock data into the Messages table where eahc messsage can only be 1 word

-- Insert some mock data into the Messages table
INSERT INTO Messages (ChatID, MessageContent, MessageTimeStamp, MessageSenderID)
VALUES
(@AliceBobChatID, 'Hi', '2024-03-06 19:00:00', @AliceID), -- Alice to Bob
(@AliceBobChatID, 'Hello', '2024-03-06 19:01:00', @BobID), -- Bob to Alice
(@AliceBobChatID, N'👋', '2024-03-06 19:02:00', @AliceID), -- Alice to Bob
(@AliceBobChatID, N'😊', '2024-03-06 19:03:00', @BobID), -- Bob to Alice
(@AliceCharlieChatID, 'Hey', '2024-03-06 19:04:00', @AliceID), -- Alice to Charlie
(@AliceCharlieChatID, 'Yo', '2024-03-06 19:05:00', @CharlieID), -- Charlie to Alice
(@AliceCharlieChatID, 'Sup?', '2024-03-06 19:06:00', @AliceID), -- Alice to Charlie
(@AliceCharlieChatID, 'NM', '2024-03-06 19:07:00', @CharlieID), -- Charlie to Alice
(@AliceDavidChatID, 'Hello', '2024-03-06 19:08:00', @AliceID), -- Alice to David
(@AliceDavidChatID, 'Hi', '2024-03-06 19:09:00', @DavidID), -- David to Alice
(@AliceDavidChatID, N'🙂', '2024-03-06 19:10:00', @AliceID), -- Alice to David
(@AliceDavidChatID, N'🙃', '2024-03-06 19:11:00', @DavidID), -- David to Alice
(@AliceEveChatID, 'Hi', '2024-03-06 19:12:00', @AliceID), -- Alice to Eve
(@AliceEveChatID, 'Pass', '2024-03-06 19:13:00', @EveID), -- Eve to Alice
(@AliceEveChatID, N'😍', '2024-03-06 19:14:00', @AliceID), -- Alice to Eve
(@AliceEveChatID, N'😘', '2024-03-06 19:15:00', @EveID), -- Eve to Alice
(@BobCharlieChatID, 'Hi', '2024-03-06 19:16:00', @BobID), -- Bob to Charlie
(@BobCharlieChatID, 'Hello', '2024-03-06 19:17:00', @CharlieID), -- Charlie to Bob
(@BobCharlieChatID, N'🌞', '2024-03-06 19:18:00', @BobID), -- Bob to Charlie
(@BobCharlieChatID, N'🌈', '2024-03-06 19:19:00', @CharlieID), -- Charlie to Bob
(@BobDavidChatID, 'Hey', '2024-03-06 19:20:00', @BobID), -- Bob to David
(@BobDavidChatID, 'Yo', '2024-03-06 19:21:00', @DavidID), -- David to Bob
(@BobDavidChatID, N'🎥', '2024-03-06 19:22:00', @BobID), -- Bob to David
(@BobDavidChatID, N'🍿', '2024-03-06 19:23:00', @DavidID), -- David to Bob
(@BobEveChatID, 'Hi', '2024-03-06 19:24:00', @BobID), -- Bob to Eve
(@BobEveChatID, 'Hake', '2024-03-06 19:25:00', @EveID), -- Eve to Bob
(@BobEveChatID, N'😎', '2024-03-06 19:26:00', @BobID), -- Bob to Eve
(@BobEveChatID, N'😁', '2024-03-06 19:27:00', @EveID), -- Eve to Bob
(@CharlieDavidChatID, 'Hello', '2024-03-06 19:28:00', @CharlieID), -- Charlie to David
(@CharlieDavidChatID, 'Hi', '2024-03-06 19:29:00', @DavidID), -- David to Charlie
(@CharlieDavidChatID, N'👍', '2024-03-06 19:30:00', @CharlieID), -- Charlie to David
(@CharlieDavidChatID, N'👌', '2024-03-06 19:31:00', @DavidID), -- David to Charlie
(@CharlieEveChatID, 'Hi', '2024-03-06 19:32:00', @CharlieID), -- Charlie to Eve
(@CharlieEveChatID, 'Defenestration', '2024-03-06 19:33:00', @EveID), -- Eve to Charlie
(@CharlieEveChatID, N'😊', '2024-03-06 19:34:00', @CharlieID), -- Charlie to Eve
(@CharlieEveChatID, N'😉', '2024-03-06 19:35:00', @EveID), -- Eve to Charlie
(@DavidEveChatID, 'Hi', '2024-03-06 19:36:00', @DavidID), -- David to Eve
(@DavidEveChatID, N'Tumble🥃', '2024-03-06 19:37:00', @EveID), -- Eve to David
(@DavidEveChatID, N'😜', '2024-03-06 19:38:00', @DavidID), -- David to Eve
(@DavidEveChatID, N'😂', '2024-03-06 19:39:00', @EveID); -- Eve to David

GO
-- Select a single chat between Alice and Bob, the data is in the messages table
SELECT * FROM Messages WHERE ChatID = 1 ORDER BY MessageTimeStamp DESC;
