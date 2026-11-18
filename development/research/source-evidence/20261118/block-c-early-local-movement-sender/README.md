# Early local movement sender evidence (2026-11-18)

This archive preserves five exact client `LocalPlayer` normal sender and teleport bookkeeping report/verdict pairs. The accepted scope is passive client source comparison only: sender selection and adjacent correction acknowledgement/response bookkeeping. It does not establish historical server movement, active network behavior, trajectory parity, broad correction equivalence, or a new product operation.

| Pair | Bounded disposition | Report SHA-256 | Verdict SHA-256 |
|---|---|---|---|
| 1.8.9→1.9 | Accepted sender slice: 1.9 preserves the changed ground-only/no-repeat behavior; first inactivity threshold is 20 vs 21. `AcceptTeleport` plus the immediate existing response does not reset periodic sender state. | `6e2777ad4a49b1a43334d3bebfab060195b57c5364f660e4f1680d881c7ae649` | `18201a4099ff90c5eac911f5287adeabb2a5807d371373ba546b56d0d5d7ab25` |
| 1.9→1.9.1 | Bounded sender/tick and response bookkeeping is unchanged; report and verdict carry exact source limitations. | `f58189ba768e0ecf488f323098b68fba53abe0810f8d12efad9f8910069b18f3` | `6d4718d65471d3fd05033cbe722e68e812d8d78f262a63144b00fa2f76b17402` |
| 1.9.1→1.9.2 | Accepted bounded no-difference for local sender selection/sent-state bookkeeping and immediate correction response. No correction arithmetic, velocity parity, server behavior, or physical replay. | `a7ae455d7ae688d92dfed8d5cee1917b08d862dd8f3f289acfdb02c83d837597` | `2a1e07a6d7070787b0cd2b8d01ec332ced97f20c4cec1a9600d67edd52180133` |
| 1.9.2→1.9.3 | Accepted bounded no-difference for ordinary local movement-packet selection and sent-state/pacing bookkeeping, plus immediate local correction response. Riding path excluded. | `61ef1f4c5806f45c7bb34bc0936d12fb0774676b759e88d3b35734ad55cf9869` | `354b8380f581f12002981fd6bdb3624e2eee60676464eb697c58ec4eca429714` |
| 1.9.3→1.9.4 | Accepted bounded no-difference for normal local sender bookkeeping and adjacent correction acknowledgement/response path. | `951a53f6cc51bb1444515d29aecfcc29658272701ee5ef232215e32dc056617a` | `6286ac043eba77b04c6aab901538d669045ca44c31c1244822bbb2a1d1b463da` |

Each frozen verdict binds the exact report hash shown here. The evidence is copied byte-for-byte. These comparisons do not imply an implementation candidate or product parity; historical server movement and active network behavior are outside this archive.