Number
==============

숫자를 입력받을 수 있는 EZBlockElement 입니다.
<hr>

## 구조
* 문자열 `description`: 입력창 위에 마우스가 올려졌을 때 뜨게 될 도움말입니다.
* 문자열 `type`: `number`라고 입력해주세요.
* 문자열 `format`: 숫자의 자료형입니다. 다음 중 하나를 입력할 수 있습니다.
    * 정수: `byte` `short` `int` `long` `integer`
    * 소수: `float` `double` `rational`
* 숫자 또는 문자열[] `range`: 입력 가능한 숫자의 범위입니다.
    * 숫자 또는 문자열 `min`: 최솟값. 숫자, 또는 각 자료형의 _MIN이나 _MAX를 입력할 수 있습니다.
    * 숫자 또는 문자열 `max`: 최대값. 숫자, 또는 각 자료형의 _MIN이나 _MAX를 입력할 수 있습니다.
* 숫자 default: 블럭이 생성될 때 기본적으로 적힐 숫자입니다.
<hr>

## 예제
`/weather` 명령어의 날씨 지속 시간 부분입니다.
```json5
{
  "duraton" : 
  {
    "description": "날씨가 지속될 기간 (단위: 초)",
    "type": "number",
    "format": "int",
    "range": [0, "INT_MAX"],
    "default": 300
  }
}
```