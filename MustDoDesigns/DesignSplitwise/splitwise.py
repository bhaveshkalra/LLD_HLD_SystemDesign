from abc import ABC, abstractmethod
from collections import defaultdict
from typing import List


class User:
    def __init__(self, user_id, name, email, mobile):
        self.user_id = user_id
        self.name = name
        self.email = email
        self.mobile = mobile

class Split(ABC):
    def __init__(self, user):
        self.user = user
        self.amount = 0

class EqualSplit(Split):
    pass

class ExactSplit(Split):
    def __init__(self, user, amount):
        super().__init__(user)
        self.amount = amount

class PercentSplit(Split):
    def __init__(self, user, percent):
        super().__init__(user)
        self.percent = percent


# ----------------- EXPENSE -----------------
class Expense:
    def __init__(self, paid_by, amount, splits: List[Split]):
        self.paid_by = paid_by
        self.amount = amount
        self.splits = splits

# ----------------- EXPENSE MANAGER -----------------
class ExpenseManager:
    def __init__(self):
        self.users = {}
        self.balances = defaultdict(lambda: defaultdict(float))

    def add_user(self, user):
        self.users[user.user_id] = user

    def add_expense(self, paid_by, amount, splits, expense_type):
        if expense_type == "EQUAL":
            self._calculate_equal(amount, splits)
        elif expense_type == "EXACT":
            self._validate_exact(amount, splits)
        elif expense_type == "PERCENT":
            self._calculate_percent(amount, splits)
        self._update_balances(paid_by, splits)

    def _calculate_equal(self, amount, splits):
        total_users = len(splits)
        equal_amount = round(amount / total_users, 2)
        total_assigned = equal_amount * total_users
        diff = round(amount - total_assigned, 2)
        splits[0].amount = round(equal_amount + diff, 2)

        for i in range(1, total_users):
            splits[i].amount = equal_amount

    def _validate_exact(self, amount, splits):
        total = round(sum(split.amount for split in splits), 2)
        if total != round(amount, 2):
            raise Exception("Exact amounts do not sum up correctly")

    def _calculate_percent(self, amount, splits):
        total_percent = sum(split.percent for split in splits)
        if total_percent != 100:
            raise Exception("Percentages do not sum to 100")
        for split in splits:
            split.amount = round((split.percent / 100) * amount, 2)

    # ----------------- UPDATE BALANCES -----------------
    def _update_balances(self, paid_by, splits):
        for split in splits:
            if split.user.user_id == paid_by.user_id:
                continue
            self.balances[paid_by.user_id][split.user.user_id] += split.amount
            self.balances[split.user.user_id][paid_by.user_id] -= split.amount

    # ----------------- SHOW BALANCES -----------------
    def show_balances(self):
        no_balance = True
        for user1 in self.balances:
            for user2 in self.balances[user1]:
                if self.balances[user1][user2] > 0:
                    print(f"{user2} owes {user1}: {round(self.balances[user1][user2], 2)}")
                    no_balance = False
        if no_balance:
            print("No balances")

    def show_balance(self, user_id):
        no_balance = True
        for user in self.balances[user_id]:
            amount = self.balances[user_id][user]
            if amount > 0:
                print(f"{user} owes {user_id}: {round(amount, 2)}")
                no_balance = False
            elif amount < 0:
                print(f"{user_id} owes {user}: {round(-amount, 2)}")
                no_balance = False
        if no_balance:
            print("No balances")


if __name__ == "__main__":
    manager = ExpenseManager()

    # Create Users
    u1 = User("u1", "User1", "u1@mail.com", "999")
    u2 = User("u2", "User2", "u2@mail.com", "999")
    u3 = User("u3", "User3", "u3@mail.com", "999")
    u4 = User("u4", "User4", "u4@mail.com", "999")

    manager.add_user(u1)
    manager.add_user(u2)
    manager.add_user(u3)
    manager.add_user(u4)

    print("------ Transaction 1: Electricity Bill (EQUAL) ------")

    # EXPENSE u1 1000 4 u1 u2 u3 u4 EQUAL
    splits1 = [
        EqualSplit(u1),
        EqualSplit(u2),
        EqualSplit(u3),
        EqualSplit(u4)
    ]
    manager.add_expense(u1, 1000, splits1, "EQUAL")
    manager.show_balances()
    print()

    print("------ Transaction 2: Flipkart BBD (EXACT) ------")

    # EXPENSE u1 1250 2 u2 u3 EXACT 370 880
    splits2 = [
        ExactSplit(u2, 370),
        ExactSplit(u3, 880)
    ]
    manager.add_expense(u1, 1250, splits2, "EXACT")
    manager.show_balances()
    print()

    print("------ Transaction 3: Dinner (PERCENT) ------")

    # EXPENSE u4 1200 4 u1 u2 u3 u4 PERCENT 40 20 20 20
    splits3 = [
        PercentSplit(u1, 40),
        PercentSplit(u2, 20),
        PercentSplit(u3, 20),
        PercentSplit(u4, 20)
    ]
    manager.add_expense(u4, 1200, splits3, "PERCENT")
    manager.show_balances()
    print()

    print("------ SHOW u1 ------")
    manager.show_balance("u1")