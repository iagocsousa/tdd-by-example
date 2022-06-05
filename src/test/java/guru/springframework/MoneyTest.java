package guru.springframework;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class MoneyTest {

  @Test
  void testMultiplication(){
    Money five = Money.dollar(5);
    assertThat(five.times(2)).isEqualTo(Money.dollar(10));
    assertThat(five.times(3)).isEqualTo(Money.dollar((15)));
  }

  @Test
  void testEquality() {
    assertThat(Money.dollar(5)).isEqualTo(Money.dollar(5));
    assertThat(Money.dollar(4)).isNotEqualTo(Money.dollar(5));
    assertThat(Money.franc(5)).isNotEqualTo(Money.dollar(5));
  }

  @Test
  void testCurrency() {
    assertThat(Money.franc(5).currency()).isEqualTo("CHF");
    assertThat(Money.dollar(4).currency()).isEqualTo("USD");
  }

  @Test
  void testSimpleAddition() {
    Money five = Money.dollar(5);
    Expression sum = five.plus(five);
    Bank bank = new Bank();
    Money reduced = bank.reduce(sum, "USD");
    assertThat(Money.dollar(10)).isEqualTo(reduced);
  }

  @Test
  void testPlusReturnsSum() {
    Money five = Money.dollar(5);
    Expression result = five.plus(five);
    Sum sum = (Sum) result;
    assertThat(five).isEqualTo(sum.augend).isEqualTo(sum.addend);

  }

  @Test
  void testReduceSum() {
    Expression sum = new Sum(Money.dollar(3) , Money.dollar(4));
    Bank bank = new Bank();
    Money result = bank.reduce(sum, "USD");
    assertThat(Money.dollar(7)).isEqualTo(result);
  }

  @Test
  void testReduceMoneyDifferentCurrency() {
    Bank bank = new Bank();
    bank.addRate("CHF", "USD",2);
    Money result = bank.reduce(Money.franc(2), "USD");
    assertThat(Money.dollar(1)).isEqualTo(result);
  }

  @Test
  void testIdentityRace() {
    assertThat(new Bank().rate("USD", "USD")).isEqualTo(1);
    assertThat(new Bank().rate("CHF", "CHF")).isEqualTo(1);
  }

  @Test
  void testMixedAddition() {
    Expression fiveBucks = Money.dollar(5);
    Expression tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF","USD",2);
    Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
    assertThat(result).isEqualTo(Money.dollar(10));
  }

  @Test
  void testSumPlusMoney() {
    Expression fiveBucks = Money.dollar(5);
    Expression tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF","USD",2);
    Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
    Money result = bank.reduce(sum, "USD");
    assertThat(result).isEqualTo(Money.dollar(15));
  }

  @Test
  void testSunTimes() {
    Expression fiveBucks = Money.dollar(5);
    Expression tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF","USD",2);
    Expression sum = new Sum(fiveBucks, tenFrancs).times(2);
    Money result = bank.reduce(sum, "USD");
    assertThat(result).isEqualTo(Money.dollar(20));
  }
}
