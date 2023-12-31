CREATE TABLE IF NOT EXISTS player(playerID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(64), username VARCHAR(64), onlineTime INT, lastSeen VARCHAR(64), coins DOUBLE, language VARCHAR(64));
CREATE TABLE IF NOT EXISTS ban(banID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(64), reason VARCHAR(64), ende VARCHAR(64), bannedByUUID VARCHAR(64));
CREATE TABLE IF NOT EXISTS globalmute(globalmute INT DEFAULT 0);
CREATE TABLE IF NOT EXISTS chatfilter(chatfilterID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, word VARCHAR(255));
CREATE TABLE IF NOT EXISTS panelUsers(panelUserID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(64), email VARBINARY(64), password VARBINARY(64), admin INT, moderator INT, supporter INT, player INT, activated INT, verifyKey VARCHAR(64), resetToken VARCHAR(64));
CREATE TABLE IF NOT EXISTS team(teamID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(64), ban INT, mute INT, report INT, commandSpy INT);
CREATE TABLE IF NOT EXISTS report(reportID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, reportedUUID VARCHAR(64), reportedUsername VARCHAR(64), reporterUUID VARCHAR(64), reporterUsername VARCHAR(64), reason VARCHAR(64), server VARCHAR(64));
CREATE TABLE IF NOT EXISTS maintance(maintance INT DEFAULT 0);