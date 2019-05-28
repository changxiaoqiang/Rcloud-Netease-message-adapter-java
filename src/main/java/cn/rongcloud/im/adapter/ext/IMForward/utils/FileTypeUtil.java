package cn.rongcloud.im.adapter.ext.IMForward.utils;

public enum FileTypeUtil {
    PNG("png", "image/png"),
    JPG("jpg", "image/jpeg"),
    MP4("mp4", "video/mp4"),
    JPEG("jpeg", "image/jpeg");

    private String ext;
    private String mimeType;

    FileTypeUtil(String ext, String mimeType) {
        this.ext = ext;
        this.mimeType = mimeType;
    }

    public String getExt() {
        return ext;
    }

    public String getMimeType() {
        return mimeType;
    }

    public static String getExt(String mimeType) {
        for (FileTypeUtil value : FileTypeUtil.values()) {
            if (value.getMimeType().equals(mimeType)) {
                return value.getExt();
            }
        }
        return null;
    }

}
