# Falling water below dry oak fence: source roster and disposition

Environment: Windows host, PowerShell. Canonical source tree read-only. Assignment requires exact Mojmap sources for 1.8.9, 1.9.4, 1.10.2 and 1.11.2.

| Exact release | Canonical mapping directory present | Ready source identity | Provenance SHA-256 | Ready marker SHA-256 | Source manifest SHA-256 | Disposition |
|---|---|---|---|---|---|---|
| 1.8.9 | `ornithe-feather` only; no `mojmap` | 1.8.9 / ornithe-feather; Feather Gen2 build.2 | `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` | `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95` | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | Missing assigned exact Mojmap source; not compared. |
| 1.9.4 | `ornithe-feather` only; no `mojmap` | 1.9.4 / ornithe-feather; Feather Gen2 build.2 | `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd` | `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6` | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | Missing assigned exact Mojmap source; not compared. |
| 1.10.2 | `ornithe-feather` only; no `mojmap` | 1.10.2 / ornithe-feather; Feather Gen2 build.2 | `13b208320bd6316d4b20eb0733f226cae151cae0ffd55ef3331a97223114adf6` | `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | Missing assigned exact Mojmap source; not compared. |
| 1.11.2 | `ornithe-feather` only; no `mojmap` | 1.11.2 / ornithe-feather; Feather Gen2 build.2 | `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6` | `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | Missing assigned exact Mojmap source; not compared. |

The ready markers' source-manifest hashes match the SHA-256 of their corresponding `ornithe-feather.sources.sha256` files. Provenance records identify the exact releases and mapping artifacts. The available Feather trees are a different mapping family than the assigned Mojmap input, so they were not substituted.

No source methods or geometry predicates were compared. The wall-adjacency predicate/face abstraction, lower horizontal neighbor, direct LocalPlayer reachability, chunk/geometry gates and resulting flow-velocity cue all remain unexamined. No equality/delta or exact alias is established; no conclusion is made across unexamined patches. No inference is made about waterlogging or newer blocks.