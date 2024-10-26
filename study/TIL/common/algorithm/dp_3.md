# 목차


# 1. 예제

**원본 링크:**

https://leetcode.com/problems/house-robber/

당신은 거리를 따라 집을 털 계획인 전문 강도입니다.
각 집에는 일정 금액의 돈이 숨겨져 있습니다.
각 집의 강도를 막는 유일한 제약은 다음과 같습니다.

1. 인접한 집에 보안 시스템이 연결되어 있다.
2. 같은 밤에 인접한 두 집에 침입한 경우 자동으로 경보가 올린다.

각 집의 금액을 나타내는 정수 배열 숫자가 주어지면 경찰에 알리지 않고
오늘 밤 도둑질할 수 있는 최대 금액을 반환하세요.

**Exmaple 1:**

```text
Input: nums = [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
Total amount you can rob = 1 + 3 = 4.
```


**Example2**

```text
Input: nums = [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
Total amount you can rob = 2 + 9 + 1 = 12.
```

**Constraints**

```text
1 <= nums.length <= 100
0 <= nums[i] <= 400
```

# 2. 주어진 상태의 문제에 답하는 함수 혹은 배열 구하기

먼저 상태 변수를 결정해야 한다.
강도가 있고 여러 개의 주택이 있을 때, 상황을 설명하는 데 필요한
유일한 변수는 현재 있는 집의 인덱스인 정수이다. 따라서 유일한 상태 변수는
집의 인덱스를 나타내는 정수 *i* 이다.

문제는 도둑질할 수 있는 최대 금액을 계산하라고 하고 있다.
그러므로 문제를 풀 때 두 가지 방식을 생각할 수 있다.

1. *i* 를 포함하여 강도가 도둑질할 수 있는 최대 금액을 반환하는 *dp(i)* 함수
2. *i* 를 포함하여 강도가 도둑질할 수 있는 최대 금액을 나타내는 *dp[i]* 를 가진 *dp* 배열

# 3. 상태 전환에 따른 반복 관계 도출

> 여기서는 top-down 방식을 선택한다.
> 일반적으로 top-down 방식이 자연스러운 사고 방식에 더 가깝기 때문에 도출하기 쉽고 이 방식으로 시작하면 이후 반복 관계를 생각하는 것이 더 쉬워진다.

우리가 어떤 집에 있다면 논리적으로 두 가지 선택을 할 수 있다.
이 집을 강탈하거나, 강탈하지 않거나이다.

1. 강탈하지 않기로 결정했다면 돈은 가져갈 수 없다. 우리가 이전 집에서 가져간 돈이 얼마가 되었든 이 집에서 갖게될 돈은 *dp(i-1)* 이다.
2. 강탈하기로 결정했다면 _nums[i]_ 라는 돈을 가져갈 수 있다. 그러나 이것은 이전 집을 털지 않았을 때만 가능하다. 이것은 강도가 현재의 집에 도착했을 때 가지고 있던 돈은 이전 집에서 훔치지 않고 가지고 있던 돈이라는 것을 의미한다. 즉 두 번째 전의 집에서의 금액을 의미하므로 *dp(i-2)* 가 된다. 현재의 집을 털고나면, *dp(i-2) + nums[i]* 의 금액이 추가된다.

# 4. 기본 케이스

- 집이 하나일 때 도둑질할 수 있는 수단은 하나뿐이다.
- 집이 두개일 때 최대 금액을 도둑질하려면 최대 금액을 가진 집을 찾아간다.

정리하면

1. *dp(0) = nums(0)*
2. *dp(1) = max(nums[0], nums[1])*

```java
class Solution {
    private int[] nums;
    private HashMap<Integer, Integer> memo = new HashMap<>();
    
    public int dp(int i) {
        if(i == 0) return nums[0];
        if(i == 1) return Math.max(nums[0], nums[1]);
        
        if(!memo.containsKey(i)) {
            memo.put(i, Math.max(dp(i-1), dp(i-2) + nums[i]));
        }
        return memo.get(i);
    }
    
    public int rob(int[] nums) {
        this.nums = nums;
        return dp(nums.length - 1);
    }
}
```