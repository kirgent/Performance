import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//@SuiteDisplayName("JUnit 4 Suite Demo")
@RunWith(Suite.class)
@Suite.SuiteClasses ({
        testAMS_Add.class,
        testAMS_Delete.class
})
class testclass_Add_Delete {

}
