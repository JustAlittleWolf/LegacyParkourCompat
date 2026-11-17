# Independent bounded source audit — ACCEPT

Pair: `1.11.2→1.12`. Candidate `pane-neighbor-face-collision.md` SHA-256 `A9D9C1ECC4AB32B8E8B805D38FDDA431A6903988BDC1BFCA624DB4980C21CA2E` verified.

## Source identity

1.11.2 ready marker `CEABFB266993567AAB8C867525365E18AA0A0D7BA5A2CB7B6CFCC83D21318C40`; manifest `D908C2AF76598AD26999563BFEF0089F1D3C8C32483EEE6B108F0DEFA2773DF0`; provenance `DCDB65E1361297A584C5B93F581B1B539911911C6CEC1A203740BAA5379732C6`. 1.12 mapping-local provenance `27515E5F4C71E7DB2F035A81716507128D77466C75380AD1EF7D140C06401467`. Source rows match: PaneBlock `1E8968E7D642358CCE90F0D3156F07F19C5BCA28D2008D5D2D4BF0ECCC7266A9` / `83862F6633B8241C3788E9C9A7E4FD0275C010EC790019B8003908996F4814B3`; Block `AB873436B24487AB0EE8055BF7A478349C7F7978398AEA8E387796A6C820996A` / `E4A90ECA411E7B0E14F5018F7385EC29891F6CDF1A1E917FA648B5712F9B33A1`; AbstractLeavesBlock identical `E57E9A22E6229305BE3D6FECA216C18F2298D465D476C3ED51BB43A4BCF13C3A`.

## Verdict

1.11.2 accepts a leaves default cube; its pane predicate has no leaves exception. In 1.12 the pane excludes AbstractLeavesBlock even though it inherits SOLID face shape. Its resolver passes the opposite-facing query direction from the adjacent neighbor position. Thus the stated leaves neighbor loses its arm bit and corresponding collision arm; world collision collection feeds player `Entity.move`. ACCEPT only for this concrete leaves case with the cited face direction preserved. Other neighbor classes/state combinations stay unresolved; no exhaustive crosswalk, pair coverage/freeze, or runtime/trajectory conclusion follows.
