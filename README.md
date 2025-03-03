
<details>
<summary><strong>Version 2 (Click to expand)</strong></summary>

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
</details>

<details>
<summary><strong>Version 1 (Click to expand)</strong></summary>

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

<details>

<summary>

## Create Book Flow

</summary>
 
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

</details>

<details>

<summary>

## Translate Text Chapter Flow

</summary>

https://github.com/stibbm/BookDataServiceSQL/assets/48364517/5d97f0b9-4df8-4872-a396-3e479fa65112

-> Starting from created text chapter page
1. Click translate chapter button -> Route to create chapter translation page
2. Specify language to translate to and name for the translated chapter, click submit -> Display spinwheel loader until completed, then route to the created translated chapter page
3. Click generate audio button -> button updates showing process has started. Once completed, page updates to contain audio player for the generated audio and a download button



</details>

1. Create text chapter in Korean
3. Generate english translation
4. Generate audio file reading translation aloud  
5. Generate video file reading translation aloud with book thumbnail as background

</details>


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

