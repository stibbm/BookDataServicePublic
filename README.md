# Book Data Service

## Service Diagram

![abc](https://github.com/user-attachments/assets/23cba135-eb9d-4edb-b7c8-c35dcf5a98a5)


## Cost Per Token = $0.01

* OpenAPI gpt-4 cost per million tokens = $30

* OpenAPI cost to translate a million chars estimate = $60.22
(based on tokens used to translate different inputs and amount of tokens used)

* Polly cost to translate a million chars = $16

* Net cost to translate and narrate a million chars = $76.22

* Price to translate chapter = (cost) * (price multiplier)

### Sufficient Coins (Count is green colored)

<img width="1792" alt="Screenshot 2025-03-14 at 11 24 50 AM" src="https://github.com/user-attachments/assets/c23accd6-1bda-496e-b794-a1fa61c6f5b0" />

### Not Enough Coins (Count is red colored)

<img width="1792" alt="notenoughcoinsred" src="https://github.com/user-attachments/assets/8d0418e5-1ff3-4921-b4d2-7bfda339f6bb" />

## Buy Translation Coins Stripe Integration

https://github.com/user-attachments/assets/5971f74d-8876-4625-930a-c14f2a773cf1

## Generate Narration Demo

https://github.com/stibbm/BookDataServicePublic/assets/48364517/b4e106a1-ad70-44f0-b024-ffecb6f30323


--------------------------

## How to run full service and site

**Start Local Stripe webhook**
```
brew install stripe/stripe-cli/stripe
stripe login
stripe listen --forward-to localhost:9190/stripeWebhooks
```

------

**How to setup database first time**

```

1. Run "docker-compose up"
2. Set spring.jpa.hibernate.ddl-auto=create in application.properties
3. Run "gradle bootRun" (Error messages will appear but it will run)
4. ctrl-c kill the run
5. Set spring.jpa.hibernate.ddl-auto=update in application.properties
6. Run "gradle bootRun"
7. DB is now setup correctly

```

***Terminal 1***
```
git clone git@github.com:stibbm/BookDataServiceSQL
cd BookDataServiceSQL
cd d2/d2
docker-compose up
```

***Terminal 2***
```
cd BookDataServiceSQL
gradle bootRun
```

-------

**BookPageContent**
```
git clone git@github.com:stibbm/BookPageContent
** Terminal 1 **
cd BookPageContent
gradle bootRun
```

**book-client-sql**
```
git clone git@github.com:stibbm/book-client-sql
cd book-client-sql
npm install
npm run start
```


## Activity Endpoints

### Account
- CreateAccount
- GetAccount

### Audio
- CreateAudio
- GetAudiosByBookNameAndChapterNumber

### Book
- CreateBook
- DeleteBook
- GetAllBooksPaged
- GetAllBooksSortedPaged
- GetBookByBookName
- GetBookByBookNumber
- GetBooksByBookTagPaged
- SearchBooksByBookTags
- SearchBooksByContent

### BookView
- CreateBookView
- GetBookViewsByBookNumber

### Chapter
- CreateChapter
- GetChapterByBookNameAndChapterNumber
- GetChapterHeadersByBookNumber
- GetChaptersByBookName
- GetChaptersByBookNamePaged

### Image
- CreateImage
- GetImagesByBookNameAndChapterNameAndImageNumber
- GetImagesByBookNameAndChapterNumberPaged

### Populate
- PopulateGitBooks
- PopulateVideoData



# Version 2

1. Books with existing untranslated chapters are listed.
2. User can add tokens to account to pay for generating next video
3. Generated video is auto-uploaded to youtube channel: (https://www.youtube.com/channel/UCXPGNL-A07Tgrd4N-7cB1Aw) and saved to S3 as a backup since youtube api limit for video uploads is very small.
* Note: Youtube channel is no longer up due to copyright strikes

https://github.com/stibbm/BookDataServicePublic/assets/48364517/b4e106a1-ad70-44f0-b024-ffecb6f30323


## Generate audiobook for specified range of chapters from admin-created available untranslated chapters flow
1. Click on generate video for specified chapters -> Popup chapter selection
2. Specify start and end chapters, click submit -> display process of creating translated audiobook showing when each step completes
     * Translating Text
     * Creating Audio
     * Creating Video
     * Uploading to Youtube
     * Completed
  
https://github.com/stibbm/BookDataServiceSQL/assets/48364517/47fde2dc-7687-4110-aa3a-ac1d9bc660cb

## Generate audiobook in english from admin-created available untranslated chapters flow
1. Click on generate next video button -> Confirm purchase popup button
2. Click on yes -> display process of creating translated audiobook showing when each step completes
     * Translating Text
     * Creating Audio
     * Creating Video
     * Uploading to Youtube
     * Completed

https://github.com/stibbm/BookDataServiceSQL/assets/48364517/971740b7-38d4-40ab-a6e3-0b8e1f1bad1a

# Version 1

## Create Book
* Required Fields:

  * Book Name
  * Description
  * Tags
  * Language
  * Thumbnail Image

## Create Chapter
* Chapters can be either:

  * Image Type
     * Chapter Name
     * File picker select numbered images
  
  * Text Type
     * Chapter Name
     * Text


## Create Book Flow

https://github.com/stibbm/BookDataServiceSQL/assets/48364517/36601998-9836-4fec-9a89-58c705850fd7

1. Click create book -> Routes to book create page
2. Specify book info and click create -> Create book and route to newly created book page
3. Click add chapter -> Routes to add chapter page
4. Select image type chapter, select numbered images, click create chapter -> Show image upload progress until completed
5. Click continue button -> Routed to newly created image chapter page
6. Click add chapter -> Routes to add chapter page
7. Select text type chapter, fill fields, click create chapter -> Once chapter is created continue button will appear
8. Click continue button -> Routes to created chapter
9. Click read first chapter button -> Routes to first chapter created
10. Click read last chapter button -> Routes to most recent chapter created


## Translate Text Chapter Flow


https://github.com/stibbm/BookDataServiceSQL/assets/48364517/5d97f0b9-4df8-4872-a396-3e479fa65112

-> Starting from created text chapter page
1. Click translate chapter button -> Route to create chapter translation page
2. Specify language to translate to and name for the translated chapter, click submit -> Display spinwheel loader until completed, then route to the created translated chapter page
3. Click generate audio button -> button updates showing process has started. Once completed, page updates to contain audio player for the generated audio and a download button



1. Create text chapter in Korean
3. Generate english translation
4. Generate audio file reading translation aloud  
5. Generate video file reading translation aloud with book thumbnail as background


### How to run
1. clone BookDataServiceSQL
   * Run "docker-compose up" in d2/d2 directory
   * Run "gradle bootRun" in base directory of repo
2. clone BookPageContent
   * Run "gradle bootRun" in base directory of repo
3. clone book-client-sql
   * Run "npm install"
   * Run "npm run start"
4. clone bookpy
   * Run "python3 m.py" to autopopulate backend data
     * Populates data for existing videos in specified playlists for connected youtube account
     * Populates data for which chapters remain unstranslated and are available to generate videos for

## Endpoints
### CreateBookActivity
#### CreateBookRequest 
* String bookName
* String bookDescription
* String bookLanguage
* Set<String> bookTags
* byte[] bookThumbnailImageBytes;
* String fileType;
<br/>

### 1. ** Create Book **

**Endpoint**: `POST /createBook`  
**Description**: Create a new book

- **Headers**  
  - `Content-Type: application/json`
  - `Authorization: <authToken>`

- **Body**
    ```json
    {
      "bookName": "wizard tower",
      "bookDescription": "book description",
      "bookLanguage": "Korean",
      "bookTags": ["tag1", "tag2"],
      "fileType": "png"
    }
    ```
  
### GetAllBooksPagedActivity
#### GetAllBooksPagedRequest
-- Note: being passed as strings and parsed into integer so any formatting errors in values don't cause an error from spring boot and can be handled within this code
* String pageNumber
* String pageSize
<br/>
  
### GetAllBooksSortedPagedActivity
#### GetAllBooksSortedPagedRequest
* String sortType (BOOK_VIEWS, CREATION_TIME, BOOK_NAME)
* String pageNumber
* String pageSize
<br/>
  
### GetBookByNameActivity
#### GetBookByNameRequest
* String bookName
<br/>

  
### GetBookByBookNumberActivity
#### GetBookByBookNumberRequest
* Long bookNumber
<br/>
  
# Database Models
## Book
@Id  
Long bookNumber  
  
String bookName  
String createdBy  
String bookDescription  
String bookLanguage  
Long bookViews  
String bookThumbnail  
Set<String> bookTags  


## Chapter
@Id  
ChapterId chapterId  

String chapterName  
Long chapterViews  
String createdBy  

### ChapterId
Long chapterNumber;  
Long bookNumber;  

## Image
@Id  
ImageId imageId  
  
String s3Key  
String s3Bucket  
String relativeImageUrl  
String createdBy  

### ImageId
Long imageNumber  
Long chapterNumber  
Long bookNumber  

  




https://github.com/stibbm/MAWNR-Translations/assets/48364517/9c91ba0c-948f-4702-8d86-3bfe028a2034

