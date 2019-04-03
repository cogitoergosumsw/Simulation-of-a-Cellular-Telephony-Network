package enums;
/*
A Fixed Channel Allocation (FCA) scheme is used. The company wants you to test at least two FCA schemes:
(a) No channel reservation
(b) 9 channels are allocated to each cell for new calls and handovers and 1 channel is reserved for handovers when the other 9 channels are not available.
This means a new call will not be allocated a channel if there is only one free channel left.
 */
public enum FCA_Schemes {
    NO_CHANNEL_RESERVATION, NINE_FREE_CHANNELS_ONE_RESERVED
}
