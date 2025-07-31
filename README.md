노트를 작성하고 관리할 수 있는 안드로이드 앱입니다.
MVVM 아키텍처를 기반으로 `Hilt`, `Room`, `ViewModel`, `StateFlow`, `DiffUtil`, `ViewBinding` 등을 활용해 구현했습니다.

## 🔧 주요 기술 스택
| 기술 | 설명 |
|------|------|
| **MVVM** | ViewModel에서 비즈니스 로직 처리, UI는 Fragment에서 처리 |
| **Hilt** | DI(Dagger 기반) 사용으로 의존성 관리 간소화 |
| **Room** | SQLite 기반 로컬 DB 구성 (Flow 반환으로 실시간 반영) |
| **StateFlow** | ViewModel과 UI 간 reactive 데이터 처리 |
| **DiffUtil + ListAdapter** | RecyclerView 효율적 갱신 |
| **ViewBinding** | 바인딩을 통한 Null 안전한 뷰 접근 |
| **SharedPreferences** | ID 자동 증가 기능 등 간단한 설정 값 저장에 사용 |



## ✨ 주요 기능

- 노트 작성 / 수정 / 삭제
- 노트 제목/날짜 기준 정렬
- `@Parcelize`로 프래그먼트 간 데이터 전달
- Fragment 생명주기 고려한 메모리 안전 바인딩 처리
