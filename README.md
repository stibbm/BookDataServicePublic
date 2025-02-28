



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

  


# Demo:
## Version 2
1. Books with existing untranslated chapters are listed.
2. User can add tokens to account to pay for generating next video
3. Generated video is auto-uploaded to youtube channel: (https://www.youtube.com/channel/UCXPGNL-A07Tgrd4N-7cB1Aw) and saved to S3 as a backup since youtube api limit for video uploads is very small.


https://github.com/stibbm/BookDataServicePublic/assets/48364517/b4e106a1-ad70-44f0-b024-ffecb6f30323



## Version 1

# Create Book
* Required Fields:

  * Book Name
  * Description
  * Tags
  * Language
  * Thumbnail Image

# Create Chapter
* Chapters can be either:

  * Image Type
     * Chapter Name
     * File picker select numbered images
  
  * Text Type
     * Chapter Name
     * Text

# Create Book Flow
  
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
 
https://github.com/stibbm/BookDataServiceSQL/assets/48364517/36601998-9836-4fec-9a89-58c705850fd7


1. Create text chapter in Korean
3. Generate english translation
4. Generate audio file reading translation aloud  
5. Generate video file reading translation aloud with book thumbnail as background

https://github.com/stibbm/MAWNR-Translations/assets/48364517/9c91ba0c-948f-4702-8d86-3bfe028a2034

