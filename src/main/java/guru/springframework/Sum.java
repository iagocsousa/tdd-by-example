package guru.springframework;

public class Sum implements Expression {

  Expression augend;
  Expression addend;

  public Sum(Expression augmend, Expression addmend) {
    this.augend = augmend;
    this.addend = addmend;
  }

  public Money reduce(Bank bank, String to) {
    int amount = augend.reduce(bank, to).amount + addend.reduce(bank, to).amount;
    return new Money(amount, to);
  }

  @Override
  public Expression plus(Expression addend) {
    return new Sum(this, addend);
  }

  @Override
  public Expression times(int multiplier) {
    return new Sum(augend.times(multiplier), addend.times(multiplier));
  }
}
