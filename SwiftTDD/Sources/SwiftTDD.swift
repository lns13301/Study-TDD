protocol Expression {
    
    func reduce(to: String) -> Money
    
}

class Money {
    
    fileprivate let amount: Int
    let currency: String
    
    init(amount: Int, currency: String) {
        self.amount = amount
        self.currency = currency
    }
    
    func times(multiplier: Int) -> Money {
        return Money(amount: amount * multiplier, currency: currency)
    }
    
    static func +(lhs: Money, rhs: Money) -> Expression {
        return Sum(augend: lhs, addend: rhs)
    }
    
    class func dollar(amount: Int) -> Money {
        return Money(amount: amount, currency: "USD")
    }
    
    class func franc(amount: Int) -> Money {
        return Money(amount: amount, currency: "CHF")
    }

}

class Bank {
    
    func reduce(source: Expression, to: String) -> Money {
        return source.reduce(to: to)
    }
    
}

struct Sum : Expression {
    
    let augend: Money
    let addend: Money
    
    func reduce(to: String) -> Money {
        return Money(amount: self.augend.amount + self.addend.amount, currency: to)
    }
    
}

extension Money : Expression {
    
    func reduce(to: String) -> Money {
        return self
    }
    
}

extension Money : Equatable {
    
    public static func ==(lhs: Money, rhs: Money) -> Bool {
        return (lhs.amount == rhs.amount) && (lhs.currency == rhs.currency)
    }
    
}





