*-----------------------------------------------------------
* Title      :
* Written by :
* Date       :
* Description:
*-----------------------------------------------------------
    ORG    $1000
START:                  ; first instruction of program

* Put program code here
    move.w  #12345,d1
    move.l  #123,d1
    move.l  #123,d1
    move.l  #123,d1
    move.l  #4096,a0
    move.b  (a0)+,d2
    move.w  #12345,d1
    move.w  #12345,d1
    move.w  #12345,d1
    SIMHALT             ; halt simulator

* Put variables and constants here

    END    START        ; last line of source

*~Font name~Courier New~
*~Font size~10~
*~Tab type~1~
*~Tab size~4~
