package book.data.service.message.book.delete;

import book.data.service.sqlmodel.book.Book;
import lombok.Builder;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteBookResponse implements Serializable {
	private Book book;
}
