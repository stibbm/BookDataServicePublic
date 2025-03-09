package book.data.service.message.book.delete;

import lombok.Builder;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import book.data.service.model.Book;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteBookResponse implements Serializable {
	private Book book;
}
