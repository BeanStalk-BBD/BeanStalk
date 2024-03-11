CREATE FUNCTION GetOpenChats(@userID int)
RETURNS TABLE
AS
RETURN
    WITH LatestMessages AS (
        SELECT ChatID, MAX(MessageTimeStamp) AS LatestMessageTimeStamp
        FROM Messages
        GROUP BY ChatID
    )
    SELECT 
        CASE
            WHEN @userID = ChatUserID1 THEN User2.UserName
            ELSE User1.UserName
        END AS OpenChatUserName,
        MAX(LM.LatestMessageTimeStamp) AS LatestMessageTimeStamp
    FROM Chat
    INNER JOIN Users AS User1 ON Chat.ChatUserID1 = User1.UserID
    INNER JOIN Users AS User2 ON Chat.ChatUserID2 = User2.UserID
    LEFT JOIN LatestMessages AS LM ON Chat.ChatID = LM.ChatID
    WHERE ChatUserID1 = @userID OR ChatUserID2 = @userID
    GROUP BY 
        CASE
            WHEN @userID = ChatUserID1 THEN ChatUserID2
            ELSE ChatUserID1
        END,
        CASE
            WHEN @userID = ChatUserID1 THEN User2.UserName
            ELSE User1.UserName
        END
