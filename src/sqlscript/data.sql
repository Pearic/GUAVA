DROP TABLE FoodType CASCADE constraints;
DROP TABLE Meats CASCADE constraints;
DROP TABLE Produce CASCADE constraints;
DROP TABLE Grains CASCADE constraints;
DROP TABLE Beverage CASCADE constraints;
DROP TABLE StorageOption CASCADE constraints;
DROP TABLE TypeCapacity CASCADE constraints;
DROP TABLE GroceryStore CASCADE constraints;
DROP TABLE AreaCode CASCADE constraints;
DROP TABLE ShoppingList CASCADE constraints;
DROP TABLE Recipe CASCADE constraints;
DROP TABLE Family CASCADE constraints;
DROP TABLE Person_Belongs_To CASCADE constraints;
DROP TABLE SeasoningType CASCADE constraints;
DROP TABLE Condiments CASCADE constraints;
DROP TABLE Spices CASCADE constraints;
DROP TABLE Herbs CASCADE constraints;
DROP TABLE Food_Stored_In CASCADE constraints;
DROP TABLE Seasoning_Stored_In CASCADE constraints;
DROP TABLE Uses CASCADE constraints;
DROP TABLE Consumes CASCADE constraints;
DROP TABLE Likes CASCADE constraints;
DROP TABLE Dislikes CASCADE constraints;
DROP TABLE Allergic_To CASCADE constraints;
DROP TABLE Purchases_Food CASCADE constraints;
DROP TABLE Purchases_Seasoning CASCADE constraints;
DROP TABLE Recipe_Contains CASCADE constraints;

CREATE TABLE FoodType (
	Food_Name		char(20),
	Calories	    integer,
	Fat		        integer,
	Sodium	        integer,
	Carbohydrate	integer,
	Protein		    integer,
	Vitamin	        integer,
	datePurchased	char(20),
	expiryDate		char(20),
	PRIMARY KEY	(Food_Name)
);

CREATE TABLE Meats (
	Food_Name		char(20),
	isFresh	integer,
	PRIMARY KEY	(Food_Name),
	FOREIGN KEY	(Food_Name) References FoodType
);

CREATE TABLE Produce (
	Food_Name 	char(20),
	partOfPlant	char(20),
	isRipe		integer,
	PRIMARY KEY	(Food_Name),
	FOREIGN KEY	(Food_Name) References FoodType
);

CREATE TABLE Grains (
	Food_Name   char(20),
	isPackaged	integer,
	isCooked	integer,
	PRIMARY KEY	(Food_Name),
	FOREIGN KEY	(Food_Name) References FoodType
);

CREATE TABLE Beverage (
	Food_Name 	char(20),
	flavour		char(20),
	isCarbonated integer,
	contentSize	integer,
	brand		char(20),
	PRIMARY KEY	(Food_Name),
	FOREIGN KEY	(Food_Name) References FoodType
);

CREATE TABLE StorageOption (
	storageID	integer,
	Type		char(20),
	Temperature	float,
	PRIMARY KEY (storageID)
);

CREATE TABLE TypeCapacity (
	Type		char(20),
	Capacity	float,
	PRIMARY KEY	(Type)
);

CREATE TABLE GroceryStore (
	storeID		integer,
	name		char(50),
	phone		integer,
	city		char(20),
	address	    char(50),
	PRIMARY KEY (storeID)
);

CREATE TABLE AreaCode (
	city		char(20),
	acode		integer,
	PRIMARY KEY 	(city)
);

CREATE TABLE ShoppingList (
	name		char(50),
	totalPrice	float,
	PRIMARY KEY 	(name)
);

CREATE TABLE Recipe (
	recipe_name		char(50),
	servingSize	integer,
	timeNeeded	integer,
	PRIMARY KEY 	(recipe_name)
);

CREATE TABLE Family (
	familyID	integer,
	lastName	char(20),
	numMembers	integer,
	PRIMARY KEY 	(familyID)
);

CREATE TABLE Person_Belongs_To (
	name		char(20),
	familyID	integer,
	age		integer,
	gender		char(20),
	role		char(20),
	PRIMARY KEY 	(name, familyID),
    FOREIGN KEY	(familyID) REFERENCES Family ON DELETE CASCADE
);


CREATE TABLE SeasoningType (
	seasoning_name		  char(20),
	price		  integer,
	datePurchased char(20),
	expiryDate	  char(20),
	PRIMARY KEY 	(seasoning_name)
);

CREATE TABLE Condiments (
	seasoning_name		char(20),
	brand		char(20),
	contentSize		integer,
	PRIMARY KEY 	(seasoning_name),
	FOREIGN KEY	(seasoning_name) REFERENCES SeasoningType
);

CREATE TABLE Spices (
	seasoning_name		char(20),
	isGrounded	integer,
	PRIMARY KEY 	(seasoning_name),
	FOREIGN KEY	(seasoning_name) REFERENCES SeasoningType
);

CREATE TABLE Herbs (
	seasoning_name		char(20),
	isDry		integer,
	PRIMARY KEY 	(seasoning_name),
	FOREIGN KEY	(seasoning_name) REFERENCES SeasoningType
);

CREATE TABLE Food_Stored_In (
	Food_Name		char(20),
	storageID	integer NOT NULL,
	PRIMARY KEY	(Food_Name, storageID),
	FOREIGN KEY 	(Food_Name) REFERENCES FoodType,
	FOREIGN KEY	(storageID) REFERENCES StorageOption
);

CREATE TABLE Seasoning_Stored_In (
	seasoning_name		char(20),
	storageID	integer NOT NULL,
	PRIMARY KEY	(seasoning_name, storageID),
	FOREIGN KEY	(seasoning_name) REFERENCES SeasoningType,
	FOREIGN KEY	(storageID) REFERENCES StorageOption
);

CREATE TABLE Uses (
	dateUsed		char(20),
	person_name		char(20),
	familyID		integer,
	seasoning_name	char(20),
	PRIMARY KEY	(person_name, familyID, seasoning_name),
	FOREIGN KEY	(person_name, familyID) REFERENCES Person_Belongs_To,
	FOREIGN KEY	(seasoning_name) REFERENCES SeasoningType
);

CREATE TABLE Consumes (
	dateConsumed	char(20),
	person_name		char(20),
	familyID		integer,
	food_name		char(20),
	PRIMARY KEY	(dateConsumed, person_name, familyID, food_name),
	FOREIGN KEY	(person_name, familyID) REFERENCES Person_Belongs_To,
	FOREIGN KEY	(food_name) REFERENCES FoodType
);

CREATE TABLE Likes (
	person_name		char(20),
	familyID		integer,
	food_name		char(20),
	PRIMARY KEY	(person_name, familyID, food_name),
	FOREIGN KEY	(person_name, familyID) REFERENCES Person_Belongs_To,
	FOREIGN KEY	(food_name) REFERENCES FoodType
);

CREATE TABLE Dislikes (
	person_name		char(20),
	familyID		integer,
	food_name		char(20),
	PRIMARY KEY	(person_name, familyID, food_name),
	FOREIGN KEY	(person_name, familyID) REFERENCES Person_Belongs_To,
	FOREIGN KEY	(food_name) REFERENCES FoodType
);


CREATE TABLE Allergic_To (
	person_name		char(20),
	familyID		integer,
	food_name		char(20),
	PRIMARY KEY	(person_name, familyID, food_name),
	FOREIGN KEY	(person_name, familyID) REFERENCES Person_Belongs_To,
	FOREIGN KEY	(food_name) REFERENCES FoodType
);


CREATE TABLE Purchases_Food (
	purchaseDate		char(20),
	food_name		char(20) NOT NULL,
	familyID		integer,
	storeID			integer,
	PRIMARY KEY	(storeID, familyID, food_name),
	FOREIGN KEY	(familyID) REFERENCES Family,
	FOREIGN KEY	(storeID) REFERENCES GroceryStore,
    FOREIGN KEY	(food_name) REFERENCES FoodType
);

CREATE TABLE Purchases_Seasoning (
	purchaseDate		char(20),
	seasoning_name	char(20) NOT NULL,
	familyID		integer,
	storeID			integer,
	PRIMARY KEY	(storeID, familyID, seasoning_name),
	FOREIGN KEY	(familyID) REFERENCES Family,
	FOREIGN KEY	(storeID) REFERENCES GroceryStore,
    FOREIGN KEY	(seasoning_name) REFERENCES SeasoningType
);

CREATE TABLE Recipe_Contains (
    recipe_name     char(50),
    seasoning_name  char(20),
    Food_Name       char(20),
    PRIMARY KEY (recipe_name, seasoning_name, Food_Name),
    FOREIGN KEY (recipe_name) REFERENCES Recipe,
    FOREIGN KEY (seasoning_name) REFERENCES SeasoningType,
    FOREIGN KEY (Food_Name) REFERENCES FoodType
);

INSERT INTO FoodType VALUES ('Tomato', 132, 6, 500, 17, 3, 50, '2020-10-13', '2020-10-24');
INSERT INTO FoodType VALUES	('Potato', 77, 0, 6, 17, 2, 55, '2020-10-13', '2020-11-13');
INSERT INTO FoodType VALUES	('Beef brisket', 155, 7, 79, 0, 21, 75, '2020-10-13', '2020-11-13');
INSERT INTO FoodType VALUES	('Salmon', 208, 13, 59, 0, 20, 90, '2020-10-21', '2020-12-21');
INSERT INTO FoodType VALUES	('Rice', 130, 0, 1, 28, 3, 15, '2020-10-20', '2022-10-20');
INSERT INTO FoodType VALUES	('Chicken leg', 172, 6, 40, 0, 28, 60, '2020-10-20', '2020-11-20');
INSERT INTO FoodType VALUES ('Pork belly', 518, 53, 32, 0, 9, 20, '2020-10-20', '2020-11-23');
INSERT INTO FoodType VALUES ('Scallop', 111, 1, 667, 5, 21, 40, '2020-10-10', '2020-12-10');
INSERT INTO FoodType VALUES ('Carrot', 41, 0, 69, 10, 1, 350, '2020-10-13', '2020-11-13');
INSERT INTO FoodType VALUES	('Onion', 40, 0, 4, 9, 1, 20, '2020-10-13', '2020-11-13');
INSERT INTO FoodType VALUES ('Cabbage', 25, 0, 18, 6, 1, 65, '2020-10-13', '2020-10-23');
INSERT INTO FoodType VALUES	('Noodles', 138, 2, 5, 25, 4.5, 10, '2020-10-20', '2021-10-20');
INSERT INTO FoodType VALUES	('Brown rice', 111, 1, 5, 23, 3, 16, '2020-10-20', '2022-10-20');
INSERT INTO FoodType VALUES	('Cereal', 378, 1, 795, 87, 6, 500, '2020-10-01', '2022-10-01');
INSERT INTO FoodType VALUES	('Oatmeal', 68, 1, 49, 12, 2, 50, '2020-10-02', '2022-10-02');
INSERT INTO FoodType VALUES	('Coca cola', 240, 0, 75, 65, 0, 0, '2020-05-30', '2022-05-30');
INSERT INTO FoodType VALUES	('Apple juice', 46, 0, 4, 11, 0, 0, '2019-03-11', '2021-03-11');
INSERT INTO FoodType VALUES ('Milk tea', 232, 0, 0, 54, 0, 0, '2020-10-13', '2021-10-13');
INSERT INTO FoodType VALUES ('Orange juice', 45, 0, 10, 0, 1, 90, '2019-07-20', '2020-07-20');
INSERT INTO FoodType VALUES ('Fanta', 250, 0, 5, 0, 0, 0, '2020-10-10', '2022-10-10');

INSERT INTO Meats VALUES ('Beef brisket', 1);
INSERT INTO Meats VALUES ('Salmon', 1);
INSERT INTO Meats VALUES ('Chicken leg', 1);
INSERT INTO Meats VALUES ('Pork belly', 1);
INSERT INTO Meats VALUES ('Scallop', 0);

INSERT INTO Produce VALUES ('Tomato', 'Fruit', 1);
INSERT INTO Produce VALUES ('Potato', 'Root', NULL);
INSERT INTO Produce VALUES ('Carrot', 'Root', NULL);
INSERT INTO Produce VALUES ('Onion', 'Root', NULL);
INSERT INTO Produce VALUES ('Cabbage', 'Leaves', NULL);

INSERT INTO Grains VALUES ('Rice', 1, 0);
INSERT INTO Grains VALUES ('Noodles', 1, 0);
INSERT INTO Grains VALUES ('Brown rice', 1, 0);
INSERT INTO Grains VALUES ('Cereal', 1, 1);
INSERT INTO Grains VALUES ('Oatmeal', 1, 0);

INSERT INTO Beverage VALUES ('Coca cola', 'Coke', 1, 355, 'Coca cola');
INSERT INTO Beverage VALUES ('Apple juice', 'Apple', 0, 200, 'Sunrype');
INSERT INTO Beverage VALUES ('Milk tea', 'Oolong milk tea', 0, 350, 'Suntory');
INSERT INTO Beverage VALUES ('Orange juice', 'Orange', 0, 200, 'Tropicana');
INSERT INTO Beverage VALUES ('Fanta', 'Orange', 1, 355, 'Coca cola');

INSERT INTO StorageOption VALUES (1, 'Fridge', 2.5);
INSERT INTO StorageOption VALUES (2, 'Freezer', 0);
INSERT INTO StorageOption VALUES  (3, 'Cabinet', 21.3);
INSERT INTO StorageOption VALUES (4, 'Basement storage', 15);
INSERT INTO StorageOption VALUES (5, 'Basement freezer', 0);

INSERT INTO TypeCapacity VALUES ('Fridge', 20);
INSERT INTO TypeCapacity VALUES ('Freezer', 25);
INSERT INTO TypeCapacity VALUES ('Cabinet', 10.5);
INSERT INTO TypeCapacity VALUES ('Basement storage', 12);
INSERT INTO TypeCapacity VALUES ('Basement freezer', 35);

INSERT INTO GroceryStore VALUES (1, 'Safeway', 9392850, 'Coquitlam', '1033 Austin Ave');
INSERT INTO GroceryStore VALUES (2, 'TNT', 4364881, 'Burnaby', '147-4800 Kingsway');
INSERT INTO GroceryStore VALUES (3, 'Save-On-Foods', 2612423, 'Vancouver', '3535 W 41st Ave');
INSERT INTO GroceryStore VALUES (4, 'FoodyWorld', 2330886, 'Richmond', '3000 Sexsmith Rd');
INSERT INTO GroceryStore VALUES (5, 'Costco', 5522228, 'Port Coquitlam', '2370 Ottawa St');

INSERT INTO AreaCode VALUES ('Coquitlam', 604);
INSERT INTO AreaCode VALUES ('Burnaby', 778);
INSERT INTO AreaCode VALUES ('Vancouver', 604);
INSERT INTO AreaCode VALUES ('Richmond', 604);
INSERT INTO AreaCode VALUES ('Port Coquitlam', 778);

INSERT INTO ShoppingList VALUES ('need eggs', 15.99);
INSERT INTO ShoppingList VALUES ('get some bread', 5.99);
INSERT INTO ShoppingList VALUES ('Thanksgiving dinner', 78.89);
INSERT INTO ShoppingList VALUES ('lunches for the week', 41.35);
INSERT INTO ShoppingList VALUES ('running out of juice', 8.98);

INSERT INTO Recipe VALUES ('Lasagna', 4, 60);
INSERT INTO Recipe VALUES ('Pepperoni pizza', 2, 120);
INSERT INTO Recipe VALUES ('Fried rice', 3, 30);
INSERT INTO Recipe VALUES ('Beef stew', 6, 180);
INSERT INTO Recipe VALUES ('Fried noodles', 2, 35);

INSERT INTO Family VALUES (1, 'Lee', 4);
INSERT INTO Family VALUES (2, 'Li', 3);
INSERT INTO Family VALUES (3, 'Pang', 5);
INSERT INTO Family VALUES (4, 'Smith', 6);
INSERT INTO Family VALUES (5, 'White', 2);

INSERT INTO Person_Belongs_To VALUES ('Shawn', 4, 19, 'male', 'son');
INSERT INTO Person_Belongs_To VALUES ('Betty', 4, 16, 'female', 'daughter');
INSERT INTO Person_Belongs_To VALUES ('Jack', 4, 45, 'male', 'father');
INSERT INTO Person_Belongs_To VALUES ('Rose', 4, 44, 'female', 'mother');
INSERT INTO Person_Belongs_To VALUES ('Julian', 4, 78, 'male', 'grandfather');

INSERT INTO SeasoningType VALUES  ('Ketchup', 5.99, '2020-01-20', '2022-01-20');
INSERT INTO SeasoningType VALUES ('Sesame oil', 3.99, '2019-04-22', '2021-04-21');
INSERT INTO SeasoningType VALUES ('Basil', 2.89, '2020-10-22', '2020-11-22');
INSERT INTO SeasoningType VALUES ('Hoisin sauce', 4.99, '2018-11-23', '2020-11-23');
INSERT INTO SeasoningType VALUES ('Peppercorn', 1.99, '2018-01-02', '2018-07-02');
INSERT INTO SeasoningType VALUES ('Mustard', 3.99, '2010-10-30', '2012-10-30');
INSERT INTO SeasoningType VALUES ('Gochujang', 5.99, '2020-10-01', '2022-10-01');
INSERT INTO SeasoningType VALUES ('Mayonnaise', 4.99, '2020-10-03', '2022-10-03');
INSERT INTO SeasoningType VALUES ('Hot sauce', 3.29, '2020-05-23', '2022-05-22');
INSERT INTO SeasoningType VALUES ('Cumin', 2.33, '2020-10-23', '2021-10-21');
INSERT INTO SeasoningType VALUES ('Turmeric', 2.99, '2020-10-30', '2020-12-30');
INSERT INTO SeasoningType VALUES ('Red chili', 1.99, '2020-04-21', '2020-10-21');
INSERT INTO SeasoningType VALUES ('Dry ginger', 3.49, '2020-03-30', '2020-09-20');
INSERT INTO SeasoningType VALUES  ('Thyme', 2.99, '2020-10-20', '2021-01-03');
INSERT INTO SeasoningType VALUES ('Chives', 3.99, '2020-10-03', '2020-10-23');
INSERT INTO SeasoningType VALUES  ('Oregano', 2.89, '2020-03-04', '2020-10-05');
INSERT INTO SeasoningType VALUES  ('Coriander', 2.79, '2020-06-04', '2020-10-04');

INSERT INTO Condiments VALUES  ('Ketchup', 'Heinz', 397);
INSERT INTO Condiments VALUES  ('Mustard', 'French', 396);
INSERT INTO Condiments VALUES ('Gochujang', 'Sunchang', 500);
INSERT INTO Condiments VALUES ('Mayonnaise', 'Hellmanns', 591);
INSERT INTO Condiments VALUES ('Hot sauce', 'Frank', 354);

INSERT INTO Spices VALUES ('Peppercorn', 1);
INSERT INTO Spices VALUES  ('Cumin', 1);
INSERT INTO Spices VALUES ('Turmeric', 0);
INSERT INTO Spices VALUES  ('Red chili', 1);
INSERT INTO Spices VALUES  ('Dry ginger', 0);


INSERT INTO Herbs VALUES ('Basil', 1);
INSERT INTO Herbs VALUES  ('Thyme', 1);
INSERT INTO Herbs VALUES ('Chives', 0);
INSERT INTO Herbs VALUES  ('Oregano', 1);
INSERT INTO Herbs VALUES  ('Coriander', 0);

INSERT INTO Food_Stored_In VALUES ('Tomato', 1);
INSERT INTO Food_Stored_In VALUES ('Potato', 1);
INSERT INTO Food_Stored_In VALUES ('Pork belly', 2);
INSERT INTO Food_Stored_In VALUES ('Salmon', 2);
INSERT INTO Food_Stored_In VALUES ('Rice', 3);

INSERT INTO Seasoning_Stored_In VALUES ('Ketchup', 1);
INSERT INTO Seasoning_Stored_In VALUES ('Sesame oil', 3);
INSERT INTO Seasoning_Stored_In VALUES ('Basil', 3);
INSERT INTO Seasoning_Stored_In VALUES ('Hoisin sauce', 1);
INSERT INTO Seasoning_Stored_In VALUES ('Peppercorn', 4);

INSERT INTO Uses VALUES  ('2020-10-22', 'Jack', 4, 'Hoisin sauce');
INSERT INTO Uses VALUES ('2020-10-22', 'Jack', 4, 'Ketchup');
INSERT INTO Uses VALUES ('2020-10-22', 'Jack', 4, 'Basil');
INSERT INTO Uses VALUES ('2020-10-22', 'Jack', 4, 'Sesame oil');
INSERT INTO Uses VALUES ('2020-10-22', 'Betty', 4, 'Ketchup');

INSERT INTO Consumes VALUES ('2020-10-21', 'Shawn', 4, 'Beef brisket');
INSERT INTO Consumes VALUES ('2020-10-21', 'Betty', 4, 'Beef brisket');
INSERT INTO Consumes VALUES ('2020-10-21', 'Jack', 4, 'Tomato');
INSERT INTO Consumes VALUES ('2020-10-21', 'Rose', 4, 'Potato');
INSERT INTO Consumes VALUES ('2020-10-21', 'Jack', 4, 'Beef brisket');

INSERT INTO Likes VALUES ('Jack', 4, 'Tomato');
INSERT INTO Likes VALUES ('Jack', 4, 'Rice');
INSERT INTO Likes VALUES ('Betty', 4, 'Beef brisket');
INSERT INTO Likes VALUES ('Rose', 4, 'Tomato');
INSERT INTO Likes VALUES ('Rose', 4, 'Potato');
INSERT INTO Likes VALUES ('Rose', 4, 'Beef brisket');
INSERT INTO Likes VALUES ('Rose', 4, 'Salmon');
INSERT INTO Likes VALUES ('Rose', 4, 'Rice');
INSERT INTO Likes VALUES ('Rose', 4, 'Chicken leg');
INSERT INTO Likes VALUES ('Rose', 4, 'Pork belly');
INSERT INTO Likes VALUES ('Rose', 4, 'Scallop');
INSERT INTO Likes VALUES ('Rose', 4, 'Carrot');
INSERT INTO Likes VALUES ('Rose', 4, 'Onion');
INSERT INTO Likes VALUES ('Rose', 4, 'Cabbage');
INSERT INTO Likes VALUES ('Rose', 4, 'Noodles');
INSERT INTO Likes VALUES ('Rose', 4, 'Brown rice');
INSERT INTO Likes VALUES ('Rose', 4, 'Cereal');
INSERT INTO Likes VALUES ('Rose', 4, 'Oatmeal');
INSERT INTO Likes VALUES ('Rose', 4, 'Coca cola');
INSERT INTO Likes VALUES ('Rose', 4, 'Apple juice');
INSERT INTO Likes VALUES ('Rose', 4, 'Milk tea');
INSERT INTO Likes VALUES ('Rose', 4, 'Orange juice');
INSERT INTO Likes VALUES ('Rose', 4, 'Fanta');

INSERT INTO Dislikes VALUES ('Jack', 4, 'Potato');
INSERT INTO Dislikes VALUES ('Jack', 4, 'Noodles');
INSERT INTO Dislikes VALUES ('Betty', 4, 'Tomato');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Tomato');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Potato');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Beef brisket');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Salmon');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Rice');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Chicken leg');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Pork belly');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Scallop');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Carrot');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Onion');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Cabbage');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Noodles');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Brown rice');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Cereal');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Oatmeal');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Coca cola');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Apple juice');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Milk tea');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Orange juice');
INSERT INTO Dislikes VALUES ('Shawn', 4, 'Fanta');

INSERT INTO Allergic_To VALUES ('Betty', 4, 'Tomato');
INSERT INTO Allergic_To VALUES ('Shawn', 4, 'Tomato');
INSERT INTO Allergic_To VALUES ('Rose', 4, 'Tomato');
INSERT INTO Allergic_To VALUES ('Jack', 4, 'Salmon');
INSERT INTO Allergic_To VALUES ('Shawn', 4, 'Salmon');

INSERT INTO Purchases_Food VALUES ('2020-10-13', 'Tomato', 4, 1);
INSERT INTO Purchases_Food VALUES ('2020-10-13', 'Beef brisket', 4, 1);
INSERT INTO Purchases_Food VALUES ('2020-10-13', 'Potato', 4, 3);
INSERT INTO Purchases_Food VALUES ('2020-10-20', 'Rice', 4, 2);
INSERT INTO Purchases_Food VALUES ('2020-10-21', 'Salmon', 4, 5);

INSERT INTO Purchases_Seasoning VALUES ('2020-10-13', 'Ketchup', 4, 1);
INSERT INTO Purchases_Seasoning  VALUES ('2020-10-20', 'Sesame oil', 4, 2);
INSERT INTO Purchases_Seasoning  VALUES ('2020-10-20', 'Hoisin sauce', 4, 2);
INSERT INTO Purchases_Seasoning  VALUES ('2020-10-13', 'Basil', 4, 3);
INSERT INTO Purchases_Seasoning  VALUES ('2020-10-13', 'Peppercorn', 4, 3);

INSERT INTO Recipe_Contains VALUES ('Lasagna', 'Basil', 'Noodles');
INSERT INTO Recipe_Contains VALUES ('Pepperoni pizza', 'Ketchup', 'Tomato');
INSERT INTO Recipe_Contains VALUES ('Fried rice', 'Hot sauce', 'Rice');
INSERT INTO Recipe_Contains VALUES ('Beef stew', 'Basil', 'Beef brisket');
INSERT INTO Recipe_Contains VALUES ('Fried noodles', 'Hoisin sauce', 'Noodles');





























