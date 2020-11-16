CREATE TABLE FoodType (
	Name		    varchar2(20),
	Calories	    integer,
	Fat		        integer,
	Sodium	        integer,
	Carbohydrate	integer,
	Protein		    integer,
	Vitamin	        integer,
	datePurchased	Date,
	expiryDate		Date,
	PRIMARY KEY	(Name)
);

CREATE TABLE Meats (
	Name		varchar2(20),
	isFresh	integer,
	PRIMARY KEY	(Name),
	FOREIGN KEY	(Name) References FoodType
);

CREATE TABLE Produce (
	Name 		varchar2(20),
	partOfPlant	varchar2(20),
	isRipe		integer,
	PRIMARY KEY	(Name),
	FOREIGN KEY	(Name) References FoodType
);

CREATE TABLE Grains (
	Name 		varchar2(20),
	isPackaged	integer,
	isCooked	integer,
	PRIMARY KEY	(Name),
	FOREIGN KEY	(Name) References FoodType
);

CREATE TABLE Beverage (
	Name 		varchar2(20),
	flavour		varchar2(20),
	isCarbonated	integer,
	size		integer,
	brand		varchar2(20),
	PRIMARY KEY	(Name),
	FOREIGN KEY	(Name) References FoodType
);

CREATE TABLE StorageOption (
	storageID	integer,
	Type		varchar2(20),
	Temperature	float
	PRIMARY KEY 	(storageID)
);

CREATE TABLE TypeCapacity (
	Type		varchar2(20),
	Capacity	float,
	PRIMARY KEY	(Type)
);

CREATE TABLE GroceryStore (
	storeID		integer,
	name		varchar2(20),
	phone		integer,
	city		varchar2(20),
	address	integer,
	PRIMARY KEY 	(storeID)
);

CREATE TABLE AreaCode (
	city		varchar2(20),
	acode		integer,
	PRIMARY KEY 	(city)
);

CREATE TABLE ShoppingList (
	name		varchar2(20),
	totalPrice	float,
	PRIMARY KEY 	(name)
);

CREATE TABLE Recipe (
	name		varchar2(20),
	servingSize	integer,
	timeNeeded	integer,
	PRIMARY KEY 	(name)
);

CREATE TABLE Family (
	familyID	integer,
	lastName	varchar2(20),
	numMembers	integer,
	PRIMARY KEY 	(familyID)
);

CREATE TABLE Person_Belongs_To (
	name		varchar2(20),
	familyID	integer,
	age		integer,
	gender		varchar2(20),
	role		varchar2(20),
	PRIMARY KEY 	(name, familyID),
    FOREIGN KEY	(familyID) REFERENCES Family ON DELETE CASCADE
);


CREATE TABLE SeasoningType (
	name		  varchar2(20),
	price		  integer,
	datePurchased Date,
	expiryDate	  Date,
	PRIMARY KEY 	(name)
);

CREATE TABLE Condiments (
	name		varchar2(20),
	brand		varchar2(20),
	size		integer,
	PRIMARY KEY 	(name),
	FOREIGN KEY	(name) REFERENCES SeasoningType
);

CREATE TABLE Spices (
	name		varchar2(20),
	isGrounded	integer,
	PRIMARY KEY 	(name),
	FOREIGN KEY	(name) REFERENCES SeasoningType
);

CREATE TABLE Herbs (
	name		varchar2(20),
	isDry		integer,
	PRIMARY KEY 	(name),
	FOREIGN KEY	(name) REFERENCES SeasoningType
);

CREATE TABLE Food_Stored_In (
	name		varchar2(20),
	storageID	integer
NOT NULL,
	PRIMARY KEY	(Name),
	FOREIGN KEY 	(Name) REFERENCES FoodType,
	FOREIGN KEY	(storageID) REFERENCES StorageOption ON DELETE NO ACTION ON UPDate CASCADE
);

CREATE TABLE Seasoning_Stored_In (
	name		varchar2(20),
	storageID	integer
NOT NULL,
	PRIMARY KEY	(name),
	FOREIGN KEY	(name) REFERENCES SeasoningType,
	FOREIGN KEY	(storageID) REFERENCES StorageOption ON DELETE NO ACTION ON UPDate CASCADE
);


CREATE TABLE Contains (
	recipe_name		varchar2(20),
	seasoning_name	varchar2(20),
	food_name		varchar2(20),
	PRIMARY KEY	(recipe_name, seasoning_name, food_name),
	FOREIGN KEY	(recipe_name) REFERENCES recipe,
	FOREIGN KEY	(seasoning_name) REFERENCES SeasoningType,
	FOREIGN KEY	(food_name) References FoodType
);

CREATE TABLE Uses (
	date			Date,
	person_name		varchar2(20),
	familyID		integer,
	seasoning_name	varchar2(20),
	PRIMARY KEY	(person_name, familyID, seasoning_name),
	FOREIGN KEY	(person_name, familyID) REFERENCES Person_Belongs_To,
	FOREIGN KEY	(seasoning_name) REFERENCES SeasoningType
);

CREATE TABLE Consumes (
	date			Date,
	person_name		varchar2(20),
	familyID		integer,
	food_name		varchar2(20),
	PRIMARY KEY	(person_name, familyID, food_name),
	FOREIGN KEY	(person_name, familyID) REFERENCES Person_Belongs_To,
	FOREIGN KEY	(food_name) REFERENCES FoodType
);

CREATE TABLE Likes (
	person_name		varchar2(20),
	familyID		integer,
	food_name		varchar2(20),
	PRIMARY KEY	(person_name, familyID, food_name),
	FOREIGN KEY	(person_name, familyID) REFERENCES Person_Belongs_To,
	FOREIGN KEY	(food_name) REFERENCES FoodType
);

CREATE TABLE Dislikes (
	person_name		varchar2(20),
	familyID		integer,
	food_name		varchar2(20),
	PRIMARY KEY	(person_name, familyID, food_name),
	FOREIGN KEY	(person_name, familyID) REFERENCES Person_Belongs_To,
	FOREIGN KEY	(food_name) REFERENCES FoodType
);


CREATE TABLE Allergic_To (
	person_name		varchar2(20),
	familyID		integer,
	food_name		varchar2(20),
	PRIMARY KEY	(person_name, familyID, food_name),
	FOREIGN KEY	(person_name, familyID) REFERENCES Person_Belongs_To,
	FOREIGN KEY	(food_name) REFERENCES FoodType
);

CREATE TABLE Puts_Food (
	food_name		varchar2(20) NOT NULL,
	familyID		integer,
	list_name		varchar2(20),
	PRIMARY KEY	(list_name, familyID, food_name),
	FOREIGN KEY	(familyID) References Family,
	FOREIGN KEY	(list_name) REFERENCES ShoppingList,
    FOREIGN KEY	(food_name) REFERENCES FoodType
);

CREATE TABLE Puts_Seasoning (
	seasoning_name	varchar2(20) NOT NULL,
	familyID		integer,
	list_name		varchar2(20),
	PRIMARY KEY	(list_name, familyID, seasoning_name),
	FOREIGN KEY	(familyID) REFERENCES Family,
	FOREIGN KEY	(list_name) REFERENCES ShoppingList,
    FOREIGN KEY	(seasoning_name) REFERENCES SeasoningType
);

CREATE TABLE Purchases_Food (
	purchaseDate		Date,
	food_name		varchar2(20) NOT NULL,
	familyID		integer,
	storeID			integer,
	PRIMARY KEY	(storeID, familyID, food_name),
	FOREIGN KEY	(familyID) REFERENCES Family,
	FOREIGN KEY	(storeID) REFERENCES GroceryStore,
    FOREIGN KEY	(food_name) REFERENCES FoodType
);

CREATE TABLE Purchases_Seasoning (
	purchaseDate		Date,
	seasoning_name	varchar2(20) NOT NULL,
	familyID		integer,
	storeID			integer,
	PRIMARY KEY	(storeID, familyID, seasoning_name),
	FOREIGN KEY	(familyID) REFERENCES Family,
	FOREIGN KEY	(storeID) REFERENCES GroceryStore,
    FOREIGN KEY	(seasoning_name) REFERENCES SeasoningType
);

INSERT INTO FoodType (Name, Calories, Fat, Sodium, Carbohydrate, Protein, Vitamin, datePurchased, expiryDate)
VALUES (("Tomato", 132, 6, 500, 17, 3, 50, 2020-10-13, 2020-10-24),
	   ("Potato", 77, 0, 6, 17, 2, 55, 2020-10-13, 2020-11-13),
	   ("Beef brisket", 155, 7, 79, 0, 21, 75, 2020-10-13, 2020-11-13),
	   ("Salmon", 208, 13, 59, 0, 20, 90, 2020-10-21, 2020-12-21),
	   ("Rice", 130, 0, 1, 28, 3, 15, 2020-10-20, 2022-10-20),
 	   ("Chicken leg", 172, 6, 40, 0, 28, 60, 2020-10-20, 2020-11-20),
	   ("Pork belly", 518, 53, 32, 0, 9, 20, 2020-10-20, 2020-11-23),
	   ("Scallop", 111, 1, 667, 5, 21, 40, 2020-10-10, 2020-12-10),
	   ("Carrot", 41, 0, 69, 10, 1, 350, 2020-10-13, 2020-11-13),
	   ("Onion", 40, 0, 4, 9, 1, 20, 2020-10-13, 2020-11-13),
	   ("Cabbage", 25, 0, 18, 6, 1, 65, 2020-10-13, 2020-10-23),
	   ("Noodles", 138, 2, 5, 25, 4.5, 10, 2020-10-20, 2021-10-20),
	   ("Brown rice", 111, 1, 5, 23, 3, 16, 2020-10-20, 2022-10-20),
	   ("Cereal", 378, 1, 795, 87, 6, 500, 2020-10-01, 2022-10-01),
	   ("Oatmeal", 68, 1, 49, 12, 2, 50, 2020-10-02, 2022-10-02),
	   ("Coca cola", 240, 0, 75, 65, 0, 0, 2020-05-30, 2022-05-30),
	   ("Apple juice", 46, 0, 4, 11, 0, 0, 2019-03-11, 2021-03-11),
       ("Milk tea", 232, 0, 0, 54, 0, 0, 2020-10-13, 2021-10-13),
       ("Orange juice", 45, 0, 10, 0, 1, 90, 2019-07-20, 2020-07-20),
       ("Fanta", 250, 0, 5, 0, 0, 0, 2020-10-10, 2022-10-10)
);

INSERT INTO Meats(Name, isFresh)
VALUES (("Beef brisket", 1),
	   ("Salmon", 1),
	   ("Chicken leg", 1),
	   ("Pork belly", 1),
	   ("Scallop", 0));

INSERT INTO Produce(Name, partOfPlant, isRipe)
VALUES (("Tomato", "Fruit", 1),
	   ("Potato", "Root", NULL),
	   ("Carrot", "Root", NULL),
	   ("Onion", "Root", NULL),
	   ("Cabbage", "Leaves", NULL));

INSERT INTO Grains(Name, isPackaged, isCooked)
VALUES (("Rice", 1, 0),
	   ("Noodles", 1, 0),
	   ("Brown rice", 1, 0),
	   ("Cereal", 1, 1),
	   ("Oatmeal", 1, 0));

INSERT INTO Beverage(Name, flavour, isCarbonated, size, brand)
VALUES (("Coca cola", "Coke", 1, 355, "Coca cola"),
	   ("Apple juice", "Apple", 0, 200, "Sunrype"),
	   ("Milk tea", "Oolong milk tea", 0, 350, "Suntory"),
	   ("Orange juice", "Orange", 0, 200, "Tropicana"),
	   ("Fanta", "Orange", 1, 355, "Coca cola"));

INSERT INTO StorageOption(storageID,Type, Temperature)
VALUES ((1, "Fridge", 2.5),
       (2, "Freezer", 0),
       (3, "Cabinet", 21.3),
	   (4, "Basement storage", 15),
	   (5, "Basement freezer", 0));

INSERT INTO TypeCapacity(type, capacity)
VALUES (("Fridge", 20),
	   ("Freezer", 25),
	   ("Cabinet", 10.5),
	   ("Basement storage", 12),
	   ("Basement freezer", 35));

INSERT INTO GroceryStore(storeID, name, phone, city, address)
VALUES ((1, "Safeway", 9392850, "Coquitlam", "1033 Austin Ave"),
	   (2, "T&T", 4364881, "Burnaby", "147-4800 Kingsway"),
	   (3, "Save-On-Foods", 2612423, "Vancouver", "3535 W 41st Ave"),
	   (4, "FoodyWorld", 2330886, "Richmond", "3000 Sexsmith Rd"),
	   (5, "Costco", 5522228, "Port Coquitlam", "2370 Ottawa St"));

INSERT INTO AreaCode(city, aCode)
VALUES (("Coquitlam", 604),
	   ("Burnaby", 778),
	   ("Vancouver", 604),
	   ("Richmond", 604),
       ("Port Coquitlam", 778));

INSERT INTO ShoppingList(name, totalPrice)
VALUES (("need eggs", 15.99),
	   ("get some bread", 5.99),
	   ("Thanksgiving dinner", 78.89),
	   ("lunches for the week", 41.35),
	   ("running out of juice", 8.98));

INSERT INTO Recipe(name, servingSize, timeNeeded)
VALUES (("Lasagna", 4, 60),
	   ("Pepperoni pizza", 2, 120),
	   ("Fried rice", 3, 30),
	   ("Beef stew", 6, 180),
  	   ("Fried noodles", 2, 35));

INSERT INTO Family(familyID, lastName, numMembers)
VALUES ((1, "Lee", 4),
	   (2, "Li", 3),
	   (3, "Pang", 5),
	   (4, "Smith", 6),
	   (5, "White", 2));

INSERT INTO Person_Belongs_To(name, familyID, age, gender, role)
VALUES (("Shawn", 4, 19, "male", "son"),
	   ("Betty", 4, 16, "female", "daughter),
	   ("Jack"", 4, 45, "male", "father"),
	   ("Rose", 4, 44, "female", "mother"),
	   ("Julian", 4, 78, "male", "grandfather"));

INSERT INTO SeasoningType(name, price, datePurchased, expiryDate)
VALUES (("Ketchup", 5.99, 2020-01-20, 2022-01-20),
	   ("Sesame oil", 3.99, 2019-04-22, 2021-04-21),
	   ("Basil", 2.89, 2020-10-22, 2020-11-22),
	   ("Hoisin sauce", 4.99, 2018-11-23, 2020-11-23),
	   ("Peppercorn", 1.99, 2018-01-02, 2018-07-02),
	   ("Mustard", 3.99, 2010-10-30, 2012-10-30),
	   ("Gochujang". 5.99, 2020-10-01, 2022-10-01),
	   ("Mayonnaise", 4.99, 2020-10-03, 2022-10-03),
	   ("Hot sauce", 3.29, 2020-05-23, 2022-05-22),
	   ("Cumin", 2.33, 2020-10-23, 2021-10-21),
	   ("Turmeric", 2.99, 2020-10-30, 2020-12-30),
	   ("Red chili", 1.99, 2020-04-21, 2020-10-21),
	   ("Dry ginger", 3.49, 2020-03-30, 2020-09-20),
	   (""Basil"", 2.49, 2020-03-20, 2020-05-29),
       ("Thyme", 2.99, 2020-10-20, 2021-01-03),
	   ("Chives", 3.99, 2020-10-03, 2020-10-23),
       ("Oregano", 2.89, 2020-03-04, 2020-10-05),
       ("Coriander", 2.79, 2020-06-04, 2020-10-04));

INSERT INTO Condiments(name, brand, size)
VALUES (("Ketchup", "Heinz" 397),
       ("Mustard", "French"s"", 396),
	   ("Gochujang", "Sunchang", 500),
	   ("Mayonnaise", "Hellmann"s"", 591)
	   ("Hot Sauce", "Frank"s"", 354));

INSERT INTO Spices(name, isGrounded)
VALUES (("Peppercorn", 1),
       ("Cumin", 1),
	   ("Turmeric", 0),
       ("Red chili", 1),
       ("Dry ginger", 0));


INSERT INTO Herbs(name, isDry)
VALUES (("Basil", 1),
       ("Thyme", 1),
	   ("Chives", 0),
       ("Oregano", 1),
       ("Coriander", 0));

INSERT INTO Food_Stored_In(name, storageID)
VALUES (("tomato", 1),
	   ("potato", 1),
	   ("pork belly", 2),
	   ("salmon", 2),
	   ("rice", 3));

INSERT INTO Seasoning_Stored_In(name, storageID)
VALUES (("Ketchup", 1),
	   ("Sesame oil", 3),
	   ("Basil", 3),
	   ("Hoisin Sauce", 1),
	   ("Peppercorn", 4));

INSERT INTO Contains(recipe_name, seasoning_name, food_name)
VALUES (("Fried rice", "Soy sauce", "Rice"),
	   ("Pepperoni pizza", "Basil", "Tomato"),
	   ("Lasagna", "Tomato sauce", "Cheese"),
	   ("Beef stew", "Pepper", "Beef brisket"),
	   ("Fried noodles", "Soy sauce", "Noodles"));

INSERT INTO Uses(date, person_name, familyID, seasoning_name)
VALUES ((2020-10-22, "Jack", 4, "Soy sauce"),
	   (2020-10-22, "Jack", 4, "Ketchup"),
	   (2020-10-22, "Jack", 4, "Basil"),
	   (2020-10-22, "Jack", 4, "Sesame oil"),
	   (2020-10-22, "Betty", 4, "Ketchup"));

INSERT INTO Consumes(date, person_name, familyID, food_name)
VALUES ((2020-10-21, "Shawn", 4, "Beef brisket"),
	   (2020-10-21, "Betty", 4, "Beef brisket"),
	   (2020-10-21, "Jack", 4, "Tomato"),
	   (2020-10-21, "Rose", 4, "Potato"),
	   (2020-10-21, "Jack", 4, "Beef brisket"));

INSERT INTO Likes(person_name, familyID, food_name)
VALUES (("Jack", 4, "Tomato");
	   ("Jack", 4, "Rice");
	   ("Betty", 4, "Beef brisket");
	   ("Shawn", 4, "Beef brisket"),
	   ("Rose", 4, "Potato"));

INSERT INTO Dislikes(person_name, familyID, food_name)
VALUES (("Jack", 4, "Potato"),
	   ("Jack", 4, "Noodles"),
	   ("Betty", 4, "Tomato"),
	   ("Shawn", 4, "Tomato"),
	   ("Shawn", 4, "Potato"));

INSERT INTO Allergic_To(person_name, familyID, food_name)
VALUES (("Betty", 4, "Tomato"),
	   ("Shawn", 4, "Tomato"),
	   ("Rose", 4, "Tomato"),
	   ("Jack", 4, “Salmon""),
	   ("Shawn", 4, "Salmon"));

INSERT INTO Puts_Food(food_name, familyID, list_name)
VALUES (("Tomato", 4, "lunches for the week"),
	   ("Beef brisket", 4, "Thanksgiving dinner"),
	   ("Salmon", 4, "Thanksgiving dinner"),
	   ("Potato", 4, "lunches of the week"),
	   ("Rice", 4, "lunches of the week"));

INSERT INTO Puts_Seasoning(seasoning_name, familyID, list_name)
VALUES (("Sesame oil", 4, "lunches for the week"),
	   ("Ketchup", 4, "Thanksgiving dinner"),
	   ("Basil", 4, "Thanksgiving dinner"),
	   ("Hoisin sauce", 4, "Thanksgiving dinner"),
	   ("Basil", 4, "lunches for the week"));

INSERT INTO Purchases_Food(purchaseDate, food_name, familyID, storeID)
VALUES ((2020-10-13, "Tomato", 4, 1),
	   (2020-10-13, "Beef brisket", 4, 1),
	   (2020-10-13, "Potato", 4, 3),
	   (2020-10-20, "Rice", 4, 2),
	   (2020-10-21, "Salmon", 4, 5));

INSERT INTO Purchases_Seasoning(purchaseDate, food_name, familyID, storeID)
VALUES ((2020-10-13, "Ketchup", 4, 1),
	   (2020-10-20, "Sesame oil", 4, 2),
	   (2020-10-20, "Hoisin sauce", 4, 2),
	   (2020-10-13, "Basil", 4, 3),
	   (2020-10-13, "Peppercorn", 4, 3));






























