package br.com.donus.donusaccountbank.domain.enums;

public enum TransactionType {

    DEPOSIT {
        @Override
        public double transformAmountValue(double amount) {
            return amount;
        }
    },
    WITHDRAWAL {
        @Override
        public double transformAmountValue(double amount) {
            return amount * -1;
        }
    };

    public abstract double transformAmountValue(double amount);
}
