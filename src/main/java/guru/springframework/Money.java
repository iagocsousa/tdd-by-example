package guru.springframework;

public class Money implements Expression{

  protected
  int amount;
  protected String currency;

  public String currency() {
    return this.currency;
  }

  protected Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  public Money times(int multiplier){
    return new Money(amount * multiplier, currency);
  }

  public static Money dollar(int amount) {
    return new Money(amount, "USD");
  }

  public static Money franc(int amount) {
    return new Money(amount, "CHF");
  }

  public Expression plus(Expression addend){
    return new Sum(this, addend);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }

    Money money = (Money) o;

    return amount == money.amount && currency.equals(money.currency());
  }

  @Override
  public int hashCode() {
    return amount;
  }

  @Override
  public Money reduce(Bank bank, String to) {
    return new Money(amount / bank.rate(currency,to),to);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Money{");
    sb.append("amount=").append(amount);
    sb.append(", currency='").append(currency).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
