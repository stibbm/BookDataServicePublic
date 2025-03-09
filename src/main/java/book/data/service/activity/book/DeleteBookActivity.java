package book.data.service.activity.book;

@Slf4j
@RestController
public class DeleteBookActivity {
  private BookManager bookManager;
  private FirebaseService firebaseService;

  @Autowired
  public DeleteBookActivity(BookManager bookManager, FirebaseService firebaseService) {
    this.bookManager = bookManager;
    this.firebaseService = firebaseService;
  }

  @PostMapping(DELETE_BOOK_BY_NAME)
  @CrossOrigin(ALL_ORIGINS)
  public @ResponseBody DeleteBookResponse deleteBook(
    @RequestBody DeleteBookRequest deleteBookRequest,
    @RequestHeader("Authorization") String authToken
  ) {
    log.info("Deleting book: " + deleteBookRequest.toString());
    String createdBy = firebaseService.getEmail(authToken);
    String deletedBook = bookManager.deleteBook(deleteBookRequest.getBookName(), createdBy);
    DeleteBookResponse deleteBookResponse = DeleteBookResponse.builder()
      .book(deletedBook)
      .build();
    return deleteBookResponse;
  }
}
