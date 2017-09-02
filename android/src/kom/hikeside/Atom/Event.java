package kom.hikeside.Atom;

/**
 * Created by Koma on 31.08.2017.
 */

public class Event {//для них будет отдельный firebase референс. mark будет содержать ссылку на event

    Action[] actionsAfter;

    enum Action{
        generate, //генерация другого mark
        disappear, //пропадание mark в т.ч механизм уничтожения себя
        reward, //награда себя или других
        check// пометка на серваке, что было выполнено, если требуется для квеста с последовательным или выборочным выполнением mark
    }



}
