<div align="center">

# 📚 Book Data Service

**A comprehensive platform for managing, translating, and generating audiobooks with automated YouTube integration**

[![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![Stripe](https://img.shields.io/badge/Stripe-008CDD?style=for-the-badge&logo=stripe&logoColor=white)](https://stripe.com/)

---

[📐 Design](#-design) •
[🎬 Demo](#-demo) •
[🚀 Installation](#-installation) •
[📡 API Reference](#-api-reference) •
[💳 Stripe Integration](#-stripe-integration) •
[💰 Pricing](#-pricing)

</div>

---

## ✨ Features

- 📖 **Book & Chapter Management** — Create and organize books with image or text-based chapters
- 🌐 **Automated Translation** — Translate content between languages using GPT-4
- 🎙️ **Audio Narration** — Generate high-quality audio using AWS Polly Neural Engine
- 🎥 **Video Generation** — Automatically create audiobook videos
- 📺 **YouTube Integration** — Direct upload to YouTube with automatic metadata
- 💎 **Token-Based Economy** — Stripe-integrated payment system for translation credits
- 🔍 **Full-Text Search** — Search books by content and tags

---

## 📐 Design

### High-Level Overview

<img width="100%" alt="C4 Diagram" src="https://github.com/user-attachments/assets/aa459efc-ecd0-442d-b738-90732d4d58e4" />

### System Architecture

<img width="100%" alt="System Architecture" src="https://github.com/user-attachments/assets/7c547a1e-6b50-46f9-b306-664eb218b65e" />

### Entity-Relationship Diagram

<img width="100%" alt="ER Diagram" src="https://github.com/user-attachments/assets/f83b90de-5fd7-4d66-84e3-43c309fa6c0c" />

---

## 🎬 Demo

### Generate Narration

https://github.com/stibbm/BookDataServicePublic/assets/48364517/b4e106a1-ad70-44f0-b024-ffecb6f30323

### Generate Audiobook for Chapter Range

https://github.com/stibbm/BookDataServiceSQL/assets/48364517/47fde2dc-7687-4110-aa3a-ac1d9bc660cb

### Create Book Flow

https://github.com/stibbm/BookDataServiceSQL/assets/48364517/36601998-9836-4fec-9a89-58c705850fd7

### Translate Text Chapter Flow

https://github.com/stibbm/BookDataServiceSQL/assets/48364517/5d97f0b9-4df8-4872-a396-3e479fa65112

---

## 🚀 Installation

### Prerequisites

- Java 17+
- Gradle
- Docker & Docker Compose
- Node.js & npm
- Stripe CLI (for payment integration)

### Quick Start

<details>
<summary><b>1️⃣ Start Stripe Webhook (Optional)</b></summary>

```bash
brew install stripe/stripe-cli/stripe
stripe login
stripe listen --forward-to localhost:9190/stripeWebhooks
```

</details>

<details>
<summary><b>2️⃣ Database Setup (First Time)</b></summary>

```bash
# Step 1: Start Docker containers
docker-compose up

# Step 2: Configure Hibernate for schema creation
# Set in application.properties:
# spring.jpa.hibernate.ddl-auto=create

# Step 3: Run the application (errors are expected)
gradle bootRun

# Step 4: Stop the application (Ctrl+C)

# Step 5: Switch to update mode
# Set in application.properties:
# spring.jpa.hibernate.ddl-auto=update

# Step 6: Run again - database is now ready!
gradle bootRun
```

</details>

<details open>
<summary><b>3️⃣ Running All Services</b></summary>

| Terminal | Service | Commands |
|:--------:|:--------|:---------|
| **1** | Database | `cd BookDataServiceSQL/d2/d2 && docker-compose up` |
| **2** | Backend API | `cd BookDataServiceSQL && gradle bootRun` |
| **3** | Page Content | `cd BookPageContent && gradle bootRun` |
| **4** | Frontend | `cd book-client-sql && npm install && npm run start` |

</details>

---

## 📡 API Reference

### Endpoints Overview

<details>
<summary><b>📚 Book Operations</b></summary>

| Endpoint | Description |
|:---------|:------------|
| `CreateBook` | Create a new book |
| `DeleteBook` | Remove a book |
| `GetAllBooksPaged` | List books with pagination |
| `GetAllBooksSortedPaged` | List sorted books with pagination |
| `GetBookByBookName` | Find book by name |
| `GetBookByBookNumber` | Find book by ID |
| `GetBooksByBookTagPaged` | Filter by tags (paginated) |
| `SearchBooksByBookTags` | Search by tags |
| `SearchBooksByContent` | Full-text content search |

</details>

<details>
<summary><b>📑 Chapter Operations</b></summary>

| Endpoint | Description |
|:---------|:------------|
| `CreateChapter` | Create a new chapter |
| `GetChapterByBookNameAndChapterNumber` | Get specific chapter |
| `GetChapterHeadersByBookNumber` | List chapter headers |
| `GetChaptersByBookName` | Get all chapters |
| `GetChaptersByBookNamePaged` | Get chapters (paginated) |

</details>

<details>
<summary><b>🖼️ Image Operations</b></summary>

| Endpoint | Description |
|:---------|:------------|
| `CreateImage` | Upload an image |
| `GetImagesByBookNameAndChapterNameAndImageNumber` | Get specific image |
| `GetImagesByBookNameAndChapterNumberPaged` | List images (paginated) |

</details>

<details>
<summary><b>🎵 Audio Operations</b></summary>

| Endpoint | Description |
|:---------|:------------|
| `CreateAudio` | Generate audio narration |
| `GetAudiosByBookNameAndChapterNumber` | Get chapter audio |

</details>

<details>
<summary><b>👤 Account & Misc</b></summary>

| Endpoint | Description |
|:---------|:------------|
| `CreateAccount` | Create user account |
| `GetAccount` | Get account details |
| `CreateBookView` | Record book view |
| `GetBookViewsByBookNumber` | Get view analytics |
| `PopulateGitBooks` | Seed book data |
| `PopulateVideoData` | Seed video data |

</details>

### Example Request

```http
POST /createBook
Content-Type: application/json
Authorization: <authToken>
```

```json
{
  "bookName": "wizard tower",
  "bookDescription": "book description",
  "bookLanguage": "Korean",
  "bookTags": ["tag1", "tag2"],
  "fileType": "png"
}
```

---

## 📊 Database Models

<details>
<summary><b>View Data Models</b></summary>

### Book

| Field | Type | Description |
|:------|:-----|:------------|
| `bookNumber` | Long | Primary Key |
| `bookName` | String | Book title |
| `createdBy` | String | Creator ID |
| `bookDescription` | String | Description |
| `bookLanguage` | String | Language code |
| `bookViews` | Long | View count |
| `bookThumbnail` | String | Thumbnail URL |
| `bookTags` | Set\<String\> | Category tags |

### Chapter

| Field | Type | Description |
|:------|:-----|:------------|
| `chapterId` | ChapterId | Composite PK (chapterNumber, bookNumber) |
| `chapterName` | String | Chapter title |
| `chapterViews` | Long | View count |
| `createdBy` | String | Creator ID |

### Image

| Field | Type | Description |
|:------|:-----|:------------|
| `imageId` | ImageId | Composite PK (imageNumber, chapterNumber, bookNumber) |
| `s3Key` | String | S3 object key |
| `s3Bucket` | String | S3 bucket name |
| `relativeImageUrl` | String | Image URL |
| `createdBy` | String | Creator ID |

</details>

---

## 💳 Stripe Integration

Seamless token purchase flow for translation credits:

https://github.com/user-attachments/assets/5971f74d-8876-4625-930a-c14f2a773cf1

### User Interface

| Sufficient Coins | Insufficient Coins |
|:----------------:|:------------------:|
| <img width="400" alt="Sufficient Coins" src="https://github.com/user-attachments/assets/c23accd6-1bda-496e-b794-a1fa61c6f5b0" /> | <img width="400" alt="Not Enough Coins" src="https://github.com/user-attachments/assets/8d0418e5-1ff3-4921-b4d2-7bfda339f6bb" /> |
| *Green indicator* | *Red indicator* |

---

## 💰 Pricing

### Pricing Model

| Component | Margin |
|:----------|:------:|
| Fixed Expenses | +30% |
| Service Runtime | +10% |
| Variability Buffer | +10% |
| **Total Markup** | **+50%** |

### Service Costs

| Service | Unit | Cost |
|:--------|:-----|-----:|
| Translation Tokens | 1,000 tokens | $10 |
| GPT-4 | 1M tokens | $30 |
| GPT-4 Translation (KO→EN) | 1M chars | $60 |
| AWS Polly Neural | 1M chars | $16 |
| Combined Translation + Narration | 1M chars | $76 |
| **User Price (incl. markup)** | 1M chars | **$114** |

---

## 📌 Version History

### Version 2 (Current)

- ✅ Browse books with existing untranslated chapters
- ✅ Token-based payment system for video generation
- ✅ Auto-upload to YouTube + S3 backup
- ✅ Range-based chapter audiobook generation

> **Note:** YouTube channel is no longer active due to copyright strikes

### Version 1

- ✅ Create books with metadata and thumbnails
- ✅ Image-type and text-type chapters
- ✅ Translation workflow
- ✅ Audio generation with AWS Polly

---

<div align="center">

**Made with ❤️ for book lovers**

</div>
