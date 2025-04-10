import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ButtonsCreatorTest {
    @Test
    void ButtonsCreatorRows_emptyList_returnsEmptyMarkup() {
        List<List<String>> emptyList = new ArrayList<>();
        InlineKeyboardMarkup markup = ButtonsCreator.createButtonRows(emptyList);
        assertNotNull(markup);
        assertTrue(markup.getKeyboard().isEmpty());
    }

    @Test
    void ButtonsCreatorRows_singleRow_returnsMarkupWithOneRow() {
        List<List<String>> singleRowList = List.of(List.of("Button1", "Button2"));
        InlineKeyboardMarkup markup = ButtonsCreator.createButtonRows(singleRowList);
        assertNotNull(markup);
        assertEquals(1, markup.getKeyboard().size());
        assertEquals(2, markup.getKeyboard().get(0).size());
        assertEquals("Button1", markup.getKeyboard().get(0).get(0).getText());
        assertEquals("Button2", markup.getKeyboard().get(0).get(1).getText());
        assertEquals("Button1", markup.getKeyboard().get(0).get(0).getCallbackData());
        assertEquals("Button2", markup.getKeyboard().get(0).get(1).getCallbackData());
    }

    @Test
    void ButtonsCreatorRows_multipleRows_returnsMarkupWithMultipleRows() {
        List<List<String>> multipleRowsList = List.of(
                List.of("Button1", "Button2"),
                List.of("Button3"),
                List.of("Button4", "Button5", "Button6")
        );
        InlineKeyboardMarkup markup = ButtonsCreator.createButtonRows(multipleRowsList);
        assertNotNull(markup);
        assertEquals(3, markup.getKeyboard().size());
        assertEquals(2, markup.getKeyboard().get(0).size());
        assertEquals(1, markup.getKeyboard().get(1).size());
        assertEquals(3, markup.getKeyboard().get(2).size());
        assertEquals("Button1", markup.getKeyboard().get(0).get(0).getText());
        assertEquals("Button6", markup.getKeyboard().get(2).get(2).getText());

        //Check CallbackData as well.
        assertEquals("Button1", markup.getKeyboard().get(0).get(0).getCallbackData());
        assertEquals("Button6", markup.getKeyboard().get(2).get(2).getCallbackData());

    }

    @Test
    void ButtonsCreatorRows_nullInnerList_throwsNullPointerException() {
        List<List<String>> listWithNull = new ArrayList<>();
        listWithNull.add(List.of("Button1"));
        listWithNull.add(null);
        assertThrows(NullPointerException.class, () -> ButtonsCreator.createButtonRows(listWithNull));
    }


    @Test
    void ButtonsCreatorRows_emptyInnerList_handlesEmptyRows() {
        List<List<String>> listWithEmpty = List.of(List.of("Button1"), new ArrayList<>(), List.of("Button2"));
        InlineKeyboardMarkup markup = ButtonsCreator.createButtonRows(listWithEmpty);
        assertNotNull(markup);
        assertEquals(3, markup.getKeyboard().size());
        assertEquals(1, markup.getKeyboard().get(0).size());
        assertEquals(0, markup.getKeyboard().get(1).size());
        assertEquals(1, markup.getKeyboard().get(2).size());
    }

    @Test
    void ButtonsCreatorRows_listWithSpaces_handlesSpacesCorrectly(){
        List<List<String>> buttonsWithSpaces = List.of(List.of("Button 1", "Button 2"));
        InlineKeyboardMarkup markup = ButtonsCreator.createButtonRows(buttonsWithSpaces);
        assertNotNull(markup);
        assertEquals(1, markup.getKeyboard().size());
        assertEquals(2, markup.getKeyboard().get(0).size());
        assertEquals("Button 1", markup.getKeyboard().get(0).get(0).getText());
        assertEquals("Button 2", markup.getKeyboard().get(0).get(1).getText());
        assertEquals("Button 1", markup.getKeyboard().get(0).get(0).getCallbackData());
        assertEquals("Button 2", markup.getKeyboard().get(0).get(1).getCallbackData());
    }

    //-----------------------------------------------------------------------------------------------------------------
    @Test
    void ButtonsCreator_emptyInput_returnsEmptyMarkup() {
        List<String> emptyList = new ArrayList<>();
        InlineKeyboardMarkup markup = ButtonsCreator.createButton(emptyList);
        assertNotNull(markup);
        assertTrue(markup.getKeyboard().isEmpty());
    }

    @Test
    void ButtonsCreator_singleButton_returnsMarkupWithOneButton() {
        List<String> singleButtonList = List.of("Button1");
        InlineKeyboardMarkup markup = ButtonsCreator.createButton(singleButtonList);
        assertNotNull(markup);
        assertEquals(1, markup.getKeyboard().size());
        assertEquals(1, markup.getKeyboard().get(0).size());
        assertEquals("Button1", markup.getKeyboard().get(0).get(0).getText());
        assertEquals("Button1", markup.getKeyboard().get(0).get(0).getCallbackData());
    }

    @Test
    void ButtonsCreator_multipleButtons_returnsMarkupWithMultipleButtons() {
        List<String> multipleButtonsList = List.of("Button1", "Button2", "Button3");
        InlineKeyboardMarkup markup = ButtonsCreator.createButton(multipleButtonsList);
        assertNotNull(markup);
        assertEquals(1, markup.getKeyboard().size());
        assertEquals(3, markup.getKeyboard().get(0).size());
        assertEquals("Button1", markup.getKeyboard().get(0).get(0).getText());
        assertEquals("Button2", markup.getKeyboard().get(0).get(1).getText());
        assertEquals("Button3", markup.getKeyboard().get(0).get(2).getText());
        assertEquals("Button1", markup.getKeyboard().get(0).get(0).getCallbackData());
        assertEquals("Button2", markup.getKeyboard().get(0).get(1).getCallbackData());
        assertEquals("Button3", markup.getKeyboard().get(0).get(2).getCallbackData());

    }

    @Test
    void ButtonsCreator_listWithSpaces_handlesSpacesCorrectly(){
        List<String> buttonsWithSpaces = List.of("Button 1", "Button 2");
        InlineKeyboardMarkup markup = ButtonsCreator.createButton(buttonsWithSpaces);
        assertNotNull(markup);
        assertEquals(1, markup.getKeyboard().size());
        assertEquals(2, markup.getKeyboard().get(0).size());
        assertEquals("Button 1", markup.getKeyboard().get(0).get(0).getText());
        assertEquals("Button 2", markup.getKeyboard().get(0).get(1).getText());
        assertEquals("Button 1", markup.getKeyboard().get(0).get(0).getCallbackData());
        assertEquals("Button 2", markup.getKeyboard().get(0).get(1).getCallbackData());
    }

}