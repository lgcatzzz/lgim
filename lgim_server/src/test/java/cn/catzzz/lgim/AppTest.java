package cn.catzzz.lgim;

import cn.catzzz.lgim.constant.MsgTypeEnum;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        System.out.println(65824333 - 65823832);
        assertTrue(true);
    }

    @Test
    public void test1() {
        MsgTypeEnum a = MsgTypeEnum.LOGIN;
        System.out.println(a.name());
        System.out.println(a == MsgTypeEnum.LOGIN);
    }
}
