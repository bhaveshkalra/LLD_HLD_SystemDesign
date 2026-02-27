"""
limit = 5 requests
window = 10 seconds
"""

#1️⃣ Fixed Window Counter
#Count requests in a fixed time window.

import time
from collections import defaultdict


class FixedWindowRateLimiter:
    def __init__(self, limit: int, window_size: int):
        self.limit = limit
        self.window_size = window_size
        self.counters = defaultdict(int)
        self.window_start = defaultdict(int)

    def allow_request(self, user_id: str) -> bool:
        current_time = int(time.time())
        window = current_time // self.window_size

        if self.window_start[user_id] != window:
            self.window_start[user_id] = window
            self.counters[user_id] = 0

        if self.counters[user_id] < self.limit:
            self.counters[user_id] += 1
            return True

        return False