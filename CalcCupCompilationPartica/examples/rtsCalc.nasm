section .data
    msg1 : db 0xA,'resultado: '
    strLen : equ $-msg1
    msg2 : db '------ end-of-program ----- ',0xA
    strLen2 : equ $-msg2
    float_sample: dd 123.456

segment .bss
    _outbuff: resb 256
    _outsz: resq 1
    _outstart: resq 1

    _inbuff: resb 256
    _insz:  resq 1
    _instart:  resq 1

section .text

global _start ; O símbolo _start é exportado para uso externo.
              ; O sistema operacional Linux espera símbolo em um programa executável.

_start:
    mov rbp, rsp ; Salvando a base da pilha.
   ; mov rax, [float_sample]
   ; mov rbx, 6

    mov rax, 123
    call _printInt

    mov rax, 1        ; 4 Código para operação de escrita
    mov rdi, 1        ; Hanlder de escrita (1 = stdout)
    mov rsi, msg2     ; Endereço da mensagem
    mov rdx, strLen2  ; Comprimento da saída
    syscall           ; Chamada ao kernel (64 bits, int 0x80  para 32 bits )

    mov rax, 60      ; Código de chamada 60 = EXIT
    mov rdi, 0       ;
    syscall          ; chamada ao kernel






; Converte um inteiro em String e o imprime para a saída padrão.
; eax contém o valor a ser impresso.
; usa esi, ebx, ecx, edx,
; sobrescreve eax,
; Não salva registradores.
_printInt:
    push rbp     ; Salvando a base da pilha;
    mov rbp, rsp ; atualizamos o topo;

    mov rsi, _outbuff   ; rsi aponta para o buffer de saida
    add rsi, 255        ;Vamos ao final do buffer !
    mov [rsi], BYTE 10  ;No final do buffer inserimos uma quebra de linha.
    dec rsi             ; rsi --

    call _intToStr

    mov rbx, _outbuff
    add rbx, 255
    sub rbx, rax
    mov [_outstart], rbx
    add rax,1
    mov [_outsz], rax


    mov rax, 1        ; 1 Código para operação de escrita
    mov rdi, 1        ; Hanlder de escrita (1 = stdout)
    mov rsi, msg1     ; Endereço da mensagem
    mov rdx, strLen  ; Comprimento da saída
    syscall           ; Chamada ao kernel (64 bits, int 0x80  para 32 bits )

    mov rax, 1           ; 1 Código para operação de escrita
    mov rdi, 1           ; Hanlder de escrita (1 = stdout)
    mov rsi, [_outstart] ; Endereço da mensagem
    mov rdx, [_outsz]    ; Comprimento da saída
    syscall              ; Chamada ao kernel (64 bits, int 0x80  para 32 bits )

    pop rbp      ;
    ret           ; retornado da função;


  ; Converte um inteiro em string
  ; rax valor p/ converter
  ; rsi ponteiro o fim do buffer onde escrever a string
  ; deixa em rax, o número de bytes escritos.
  ; usa rcx, rbx não salva registradores.
_intToStr:
    mov rbx, rsi              ; copia o end do fim do buffer
    mov rcx, 10               ; base da conversao
_tostr:
    mov rdx,0                 ; edx  (a parte mais alta )
    idiv rcx                  ; divide edx:eax por ecx
    add dl, 48                ; 48 é código ascii para '0'
    mov [rsi], dl;            ; colocamos o carácter obtido em dl no valor apontado por ebx
    dec rsi                   ; esi --
    cmp rax,0                 ;
    jnz _tostr              ; Se eax > 0; volte e conte outra vez !!!

    inc rsi           ; Calcula o início do número e seu comprimento na string de saída.
    sub rbx, rsi
    mov rax, rbx
    add rax, 1
    ret


