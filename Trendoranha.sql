USE [ShopDB1]
GO
/****** Object:  Table [dbo].[chat_lieu]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[chat_lieu](
	[id_chat_lieu] [int] IDENTITY(1,1) NOT NULL,
	[ma] [varchar](20) NOT NULL,
	[chat_lieu] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_chat_lieu] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[co_ao]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[co_ao](
	[id_co_ao] [int] IDENTITY(1,1) NOT NULL,
	[ma] [varchar](20) NOT NULL,
	[co_ao] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_co_ao] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[danh_muc]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[danh_muc](
	[id_danh_muc] [int] IDENTITY(1,1) NOT NULL,
	[ten_danh_muc] [nvarchar](100) NOT NULL,
	[ma_danh_muc] [nvarchar](50) NOT NULL,
	[trang_thai] [tinyint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_danh_muc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[duong_may]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[duong_may](
	[id_duong_may] [int] IDENTITY(1,1) NOT NULL,
	[ma] [varchar](20) NOT NULL,
	[duong_may] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_duong_may] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[giam_gia]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[giam_gia](
	[id_gg] [int] IDENTITY(1,1) NOT NULL,
	[ma_giam_gia] [varchar](20) NOT NULL,
	[ten_giam_gia] [nvarchar](100) NOT NULL,
	[ngay_bat_dau] [datetime] NOT NULL,
	[ngay_ket_thuc] [datetime] NOT NULL,
	[loai_giam_gia] [nvarchar](100) NOT NULL,
	[gia_tri_giam] [int] NULL,
	[so_luong] [int] NOT NULL,
	[trang_thai] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_gg] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hinh_thuc_thanh_toan]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hinh_thuc_thanh_toan](
	[id_hinh_thuc] [int] IDENTITY(1,1) NOT NULL,
	[ten_hinh_thuc] [nvarchar](50) NOT NULL,
	[trang_thai] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_hinh_thuc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hoa_don]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hoa_don](
	[id_hoa_don] [int] IDENTITY(1,1) NOT NULL,
	[id_nv] [int] NOT NULL,
	[id_kh] [int] NOT NULL,
	[id_gg] [int] NULL,
	[id_hinh_thuc] [int] NOT NULL,
	[ma_hd] [varchar](20) NOT NULL,
	[ngay_tao] [datetime] NOT NULL,
	[tong_tien] [int] NULL,
	[trang_thai] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_hoa_don] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hoa_don_chi_tiet]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hoa_don_chi_tiet](
	[id_hdct] [int] IDENTITY(1,1) NOT NULL,
	[id_hoa_don] [int] NOT NULL,
	[id_sp] [int] NOT NULL,
	[ma_hdct] [varchar](20) NOT NULL,
	[don_gia] [int] NULL,
	[so_luong] [int] NOT NULL,
	[thanh_tien] [int] NULL,
	[trang_thai] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_hdct] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hoa_tiet]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hoa_tiet](
	[id_hoa_tiet] [int] IDENTITY(1,1) NOT NULL,
	[ma] [varchar](20) NOT NULL,
	[hoa_tiet] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_hoa_tiet] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[khach_hang]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[khach_hang](
	[id_kh] [int] IDENTITY(1,1) NOT NULL,
	[ma_kh] [varchar](20) NOT NULL,
	[ten_kh] [nvarchar](50) NOT NULL,
	[trang_thai] [int] NOT NULL,
	[dia_chi] [nvarchar](100) NULL,
	[sdt] [varchar](15) NULL,
	[gioi_tinh] [bit] NULL,
	[ngay_sinh] [date] NULL,
	[quoc_tich] [nvarchar](30) NULL,
	[email] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_kh] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[khuy_ao]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[khuy_ao](
	[id_khuy_ao] [int] IDENTITY(1,1) NOT NULL,
	[ma] [varchar](20) NOT NULL,
	[khuy_ao] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_khuy_ao] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[kich_thuoc]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[kich_thuoc](
	[id_kich_thuoc] [int] IDENTITY(1,1) NOT NULL,
	[ma] [varchar](20) NOT NULL,
	[kich_thuoc] [nvarchar](10) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_kich_thuoc] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[kieu_dang]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[kieu_dang](
	[id_kieu_dang] [int] IDENTITY(1,1) NOT NULL,
	[ma] [varchar](20) NOT NULL,
	[kieu_dang] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_kieu_dang] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[lich_su_hoa_don]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[lich_su_hoa_don](
	[id_lich_su_hoa_don] [int] IDENTITY(1,1) NOT NULL,
	[id_hoa_don] [int] NOT NULL,
	[ma_lich_su] [varchar](20) NOT NULL,
	[ghi_chu] [nvarchar](255) NULL,
	[trang_thai] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_lich_su_hoa_don] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[mau_sac]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mau_sac](
	[id_mau_sac] [int] IDENTITY(1,1) NOT NULL,
	[ma] [varchar](20) NOT NULL,
	[mau_sac] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_mau_sac] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[nhan_vien]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[nhan_vien](
	[id_nv] [int] IDENTITY(1,1) NOT NULL,
	[ma_nv] [varchar](20) NOT NULL,
	[ten_nv] [nvarchar](50) NOT NULL,
	[email] [varchar](50) NOT NULL,
	[sdt] [varchar](15) NULL,
	[cccd] [varchar](20) NULL,
	[gioi_tinh] [bit] NULL,
	[ngay_sinh] [date] NULL,
	[dia_chi] [nvarchar](100) NULL,
	[trang_thai] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_nv] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[phong_cach]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[phong_cach](
	[id_phong_cach] [int] IDENTITY(1,1) NOT NULL,
	[ma] [varchar](20) NOT NULL,
	[phong_cach] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_phong_cach] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[san_pham]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[san_pham](
	[id_san_pham] [int] IDENTITY(1,1) NOT NULL,
	[id_co_ao] [int] NOT NULL,
	[id_khuy_ao] [int] NOT NULL,
	[id_kieu_dang] [int] NOT NULL,
	[id_duong_may] [int] NOT NULL,
	[id_hoa_tiet] [int] NOT NULL,
	[id_phong_cach] [int] NOT NULL,
	[id_chat_lieu] [int] NOT NULL,
	[id_mau_sac] [int] NOT NULL,
	[id_kich_thuoc] [int] NOT NULL,
	[id_tay_ao] [int] NOT NULL,
	[ten_san_pham] [nvarchar](100) NOT NULL,
	[anh_san_pham] [nvarchar](255) NULL,
	[so_luong] [int] NOT NULL,
	[gia] [int] NULL,
	[trang_thai] [int] NOT NULL,
	[ngay] [datetime] NOT NULL,
	[ma_san_pham] [varchar](50) NOT NULL,
	[mo_ta] [nvarchar](255) NULL,
	[id_danh_muc] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_san_pham] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tai_khoan]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tai_khoan](
	[id_tai_khoan] [int] IDENTITY(1,1) NOT NULL,
	[id_nv] [int] NULL,
	[id_kh] [int] NULL,
	[ten_dang_nhap] [varchar](50) NOT NULL,
	[email] [varchar](50) NULL,
	[loai_tai_khoan] [int] NOT NULL,
	[trang_thai] [int] NOT NULL,
	[mat_khau] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id_tai_khoan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tay_ao]    Script Date: 6/27/2025 8:22:39 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tay_ao](
	[id_tay_ao] [int] IDENTITY(1,1) NOT NULL,
	[ma] [varchar](20) NOT NULL,
	[tay_ao] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_tay_ao] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[chat_lieu] ON 

INSERT [dbo].[chat_lieu] ([id_chat_lieu], [ma], [chat_lieu]) VALUES (1, N'CL001', N'Vải cotton')
INSERT [dbo].[chat_lieu] ([id_chat_lieu], [ma], [chat_lieu]) VALUES (2, N'CL002', N'Vải linen')
INSERT [dbo].[chat_lieu] ([id_chat_lieu], [ma], [chat_lieu]) VALUES (4, N'CL004', N'Vai nito')
INSERT [dbo].[chat_lieu] ([id_chat_lieu], [ma], [chat_lieu]) VALUES (5, N'CA003', N'vãi nhung')
INSERT [dbo].[chat_lieu] ([id_chat_lieu], [ma], [chat_lieu]) VALUES (6, N'CA003', N'Cổ Tròn Rộng')
SET IDENTITY_INSERT [dbo].[chat_lieu] OFF
GO
SET IDENTITY_INSERT [dbo].[co_ao] ON 

INSERT [dbo].[co_ao] ([id_co_ao], [ma], [co_ao]) VALUES (1, N'CA001', N'Cổ tròn')
INSERT [dbo].[co_ao] ([id_co_ao], [ma], [co_ao]) VALUES (2, N'CA002', N'Cổ tim')
INSERT [dbo].[co_ao] ([id_co_ao], [ma], [co_ao]) VALUES (3, N'CA003', N'Cổ Tròn Ngắn')
SET IDENTITY_INSERT [dbo].[co_ao] OFF
GO
SET IDENTITY_INSERT [dbo].[danh_muc] ON 

INSERT [dbo].[danh_muc] ([id_danh_muc], [ten_danh_muc], [ma_danh_muc], [trang_thai]) VALUES (1, N'Áo phông Nike', N'MADM001', 1)
INSERT [dbo].[danh_muc] ([id_danh_muc], [ten_danh_muc], [ma_danh_muc], [trang_thai]) VALUES (2, N'Áo phông Adidas', N'MADM002', 1)
INSERT [dbo].[danh_muc] ([id_danh_muc], [ten_danh_muc], [ma_danh_muc], [trang_thai]) VALUES (3, N'Áo phông Uniqlo', N'MADM003', 1)
INSERT [dbo].[danh_muc] ([id_danh_muc], [ten_danh_muc], [ma_danh_muc], [trang_thai]) VALUES (4, N'Áo phông Dior', N'MADM004', 1)
INSERT [dbo].[danh_muc] ([id_danh_muc], [ten_danh_muc], [ma_danh_muc], [trang_thai]) VALUES (5, N'Áo phông Zara', N'MADM005', 1)
SET IDENTITY_INSERT [dbo].[danh_muc] OFF
GO
SET IDENTITY_INSERT [dbo].[duong_may] ON 

INSERT [dbo].[duong_may] ([id_duong_may], [ma], [duong_may]) VALUES (1, N'DM001', N'Đường may thẳng')
INSERT [dbo].[duong_may] ([id_duong_may], [ma], [duong_may]) VALUES (2, N'DM002', N'Đường may chéo')
SET IDENTITY_INSERT [dbo].[duong_may] OFF
GO
SET IDENTITY_INSERT [dbo].[giam_gia] ON 

INSERT [dbo].[giam_gia] ([id_gg], [ma_giam_gia], [ten_giam_gia], [ngay_bat_dau], [ngay_ket_thuc], [loai_giam_gia], [gia_tri_giam], [so_luong], [trang_thai]) VALUES (1, N'GG001', N'Giảm 10%', CAST(N'2025-01-01T00:00:00.000' AS DateTime), CAST(N'2025-06-25T00:00:00.000' AS DateTime), N'Tiền mặt', 100000, 100, 1)
INSERT [dbo].[giam_gia] ([id_gg], [ma_giam_gia], [ten_giam_gia], [ngay_bat_dau], [ngay_ket_thuc], [loai_giam_gia], [gia_tri_giam], [so_luong], [trang_thai]) VALUES (2, N'GG002', N'Giảm 20%', CAST(N'2025-05-01T00:00:00.000' AS DateTime), CAST(N'2025-06-19T00:00:00.000' AS DateTime), N'Phần trăm', 20, 50, 1)
INSERT [dbo].[giam_gia] ([id_gg], [ma_giam_gia], [ten_giam_gia], [ngay_bat_dau], [ngay_ket_thuc], [loai_giam_gia], [gia_tri_giam], [so_luong], [trang_thai]) VALUES (3, N'GG003', N'Giảm 30%', CAST(N'2025-01-01T00:00:00.000' AS DateTime), CAST(N'2025-12-31T00:00:00.000' AS DateTime), N'Tiền mặt', 30, 100, 1)
INSERT [dbo].[giam_gia] ([id_gg], [ma_giam_gia], [ten_giam_gia], [ngay_bat_dau], [ngay_ket_thuc], [loai_giam_gia], [gia_tri_giam], [so_luong], [trang_thai]) VALUES (4, N'GG004', N'Giảm 50%', CAST(N'2025-01-01T00:00:00.000' AS DateTime), CAST(N'2025-12-31T00:00:00.000' AS DateTime), N'Tiền mặt', 50, 50, 1)
SET IDENTITY_INSERT [dbo].[giam_gia] OFF
GO
SET IDENTITY_INSERT [dbo].[hinh_thuc_thanh_toan] ON 

INSERT [dbo].[hinh_thuc_thanh_toan] ([id_hinh_thuc], [ten_hinh_thuc], [trang_thai]) VALUES (1, N'Tiền mặt', 1)
INSERT [dbo].[hinh_thuc_thanh_toan] ([id_hinh_thuc], [ten_hinh_thuc], [trang_thai]) VALUES (2, N'Chuyển khoản', 1)
INSERT [dbo].[hinh_thuc_thanh_toan] ([id_hinh_thuc], [ten_hinh_thuc], [trang_thai]) VALUES (3, N'Ví điện tử', 1)
SET IDENTITY_INSERT [dbo].[hinh_thuc_thanh_toan] OFF
GO
SET IDENTITY_INSERT [dbo].[hoa_don] ON 

INSERT [dbo].[hoa_don] ([id_hoa_don], [id_nv], [id_kh], [id_gg], [id_hinh_thuc], [ma_hd], [ngay_tao], [tong_tien], [trang_thai]) VALUES (1, 1, 1, 1, 1, N'HD001', CAST(N'2025-05-21T19:41:06.513' AS DateTime), 315000, 0)
INSERT [dbo].[hoa_don] ([id_hoa_don], [id_nv], [id_kh], [id_gg], [id_hinh_thuc], [ma_hd], [ngay_tao], [tong_tien], [trang_thai]) VALUES (2, 2, 2, NULL, 2, N'HD002', CAST(N'2025-05-21T19:41:06.513' AS DateTime), 760000, 1)
SET IDENTITY_INSERT [dbo].[hoa_don] OFF
GO
SET IDENTITY_INSERT [dbo].[hoa_don_chi_tiet] ON 

INSERT [dbo].[hoa_don_chi_tiet] ([id_hdct], [id_hoa_don], [id_sp], [ma_hdct], [don_gia], [so_luong], [thanh_tien], [trang_thai]) VALUES (1, 1, 1, N'HDCT001', 350000, 1, 350000, 0)
INSERT [dbo].[hoa_don_chi_tiet] ([id_hdct], [id_hoa_don], [id_sp], [ma_hdct], [don_gia], [so_luong], [thanh_tien], [trang_thai]) VALUES (2, 2, 2, N'HDCT002', 380000, 2, 760000, 1)
SET IDENTITY_INSERT [dbo].[hoa_don_chi_tiet] OFF
GO
SET IDENTITY_INSERT [dbo].[hoa_tiet] ON 

INSERT [dbo].[hoa_tiet] ([id_hoa_tiet], [ma], [hoa_tiet]) VALUES (1, N'HT001', N'Hoa văn sọc')
INSERT [dbo].[hoa_tiet] ([id_hoa_tiet], [ma], [hoa_tiet]) VALUES (2, N'HT002', N'Hoa văn chấm bi')
INSERT [dbo].[hoa_tiet] ([id_hoa_tiet], [ma], [hoa_tiet]) VALUES (3, N'HT003', N'Họa Tiết Có Cầu Vòng')
SET IDENTITY_INSERT [dbo].[hoa_tiet] OFF
GO
SET IDENTITY_INSERT [dbo].[khach_hang] ON 

INSERT [dbo].[khach_hang] ([id_kh], [ma_kh], [ten_kh], [trang_thai], [dia_chi], [sdt], [gioi_tinh], [ngay_sinh], [quoc_tich], [email]) VALUES (1, N'KH001', N'Nguyễn Văn A', 1, N'123 Đường A, Quận 1', N'0909123456', 1, CAST(N'2025-06-02' AS Date), N'Việt Nam', N'nguyenvana@example.com')
INSERT [dbo].[khach_hang] ([id_kh], [ma_kh], [ten_kh], [trang_thai], [dia_chi], [sdt], [gioi_tinh], [ngay_sinh], [quoc_tich], [email]) VALUES (2, N'KH002', N'Trần Thị B', 1, N'456 Đường B, Quận 2', N'0912345678', 0, CAST(N'1992-05-12' AS Date), N'Việt Nam', N'tranthib@example.com')
INSERT [dbo].[khach_hang] ([id_kh], [ma_kh], [ten_kh], [trang_thai], [dia_chi], [sdt], [gioi_tinh], [ngay_sinh], [quoc_tich], [email]) VALUES (3, N'KH003', N'Trần Văn Lan', 1, N'123 Đường A, Quận 10', N'0909123452', 1, CAST(N'2025-06-02' AS Date), N'Việt Nam', N'phamlanc@example.com')
INSERT [dbo].[khach_hang] ([id_kh], [ma_kh], [ten_kh], [trang_thai], [dia_chi], [sdt], [gioi_tinh], [ngay_sinh], [quoc_tich], [email]) VALUES (4, N'KH004', N'baotranquoc123', 1, N'456 Đường B, Quận 7', N'0909123222', 1, CAST(N'2025-02-02' AS Date), N'Sài Gòn', N'bao2004tk@gmail.com')
SET IDENTITY_INSERT [dbo].[khach_hang] OFF
GO
SET IDENTITY_INSERT [dbo].[khuy_ao] ON 

INSERT [dbo].[khuy_ao] ([id_khuy_ao], [ma], [khuy_ao]) VALUES (1, N'KA001', N'Khuy tròn')
INSERT [dbo].[khuy_ao] ([id_khuy_ao], [ma], [khuy_ao]) VALUES (2, N'KA002', N'Khuy vuông')
INSERT [dbo].[khuy_ao] ([id_khuy_ao], [ma], [khuy_ao]) VALUES (3, N'KA003', N'Khuy áo chữ nhật')
SET IDENTITY_INSERT [dbo].[khuy_ao] OFF
GO
SET IDENTITY_INSERT [dbo].[kich_thuoc] ON 

INSERT [dbo].[kich_thuoc] ([id_kich_thuoc], [ma], [kich_thuoc]) VALUES (1, N'KT001', N'S')
INSERT [dbo].[kich_thuoc] ([id_kich_thuoc], [ma], [kich_thuoc]) VALUES (2, N'KT002', N'M')
INSERT [dbo].[kich_thuoc] ([id_kich_thuoc], [ma], [kich_thuoc]) VALUES (3, N'KT003', N'XL')
SET IDENTITY_INSERT [dbo].[kich_thuoc] OFF
GO
SET IDENTITY_INSERT [dbo].[kieu_dang] ON 

INSERT [dbo].[kieu_dang] ([id_kieu_dang], [ma], [kieu_dang]) VALUES (1, N'KD001', N'Kiểu dáng ôm')
INSERT [dbo].[kieu_dang] ([id_kieu_dang], [ma], [kieu_dang]) VALUES (2, N'KD002', N'Kiểu dáng suông')
INSERT [dbo].[kieu_dang] ([id_kieu_dang], [ma], [kieu_dang]) VALUES (3, N'KD003', N'Kiểu dáng bó sát')
SET IDENTITY_INSERT [dbo].[kieu_dang] OFF
GO
SET IDENTITY_INSERT [dbo].[lich_su_hoa_don] ON 

INSERT [dbo].[lich_su_hoa_don] ([id_lich_su_hoa_don], [id_hoa_don], [ma_lich_su], [ghi_chu], [trang_thai]) VALUES (1, 1, N'LSHD001', N'Khởi tạo hóa đơn', 1)
INSERT [dbo].[lich_su_hoa_don] ([id_lich_su_hoa_don], [id_hoa_don], [ma_lich_su], [ghi_chu], [trang_thai]) VALUES (2, 2, N'LSHD002', N'Khách hàng thanh toán', 1)
SET IDENTITY_INSERT [dbo].[lich_su_hoa_don] OFF
GO
SET IDENTITY_INSERT [dbo].[mau_sac] ON 

INSERT [dbo].[mau_sac] ([id_mau_sac], [ma], [mau_sac]) VALUES (1, N'MS001', N'Màu trắng')
INSERT [dbo].[mau_sac] ([id_mau_sac], [ma], [mau_sac]) VALUES (2, N'MS002', N'Màu đen')
INSERT [dbo].[mau_sac] ([id_mau_sac], [ma], [mau_sac]) VALUES (3, N'MS003', N'Màu Cam')
SET IDENTITY_INSERT [dbo].[mau_sac] OFF
GO
SET IDENTITY_INSERT [dbo].[nhan_vien] ON 

INSERT [dbo].[nhan_vien] ([id_nv], [ma_nv], [ten_nv], [email], [sdt], [cccd], [gioi_tinh], [ngay_sinh], [dia_chi], [trang_thai]) VALUES (1, N'NV001', N'Phạm Văn C', N'phamvanc@example.com', N'0987654321', N'123456789', 1, CAST(N'1985-10-20' AS Date), N'789 Đường C, Quận 3', 1)
INSERT [dbo].[nhan_vien] ([id_nv], [ma_nv], [ten_nv], [email], [sdt], [cccd], [gioi_tinh], [ngay_sinh], [dia_chi], [trang_thai]) VALUES (2, N'NV002', N'Lê Thị D', N'lethid@example.com', N'0909123456', N'987654321', 0, CAST(N'1990-03-15' AS Date), N'321 Đường D, Quận 4', 1)
SET IDENTITY_INSERT [dbo].[nhan_vien] OFF
GO
SET IDENTITY_INSERT [dbo].[phong_cach] ON 

INSERT [dbo].[phong_cach] ([id_phong_cach], [ma], [phong_cach]) VALUES (1, N'PC001', N'Phong cách trẻ trung')
INSERT [dbo].[phong_cach] ([id_phong_cach], [ma], [phong_cach]) VALUES (2, N'PC002', N'Phong cách công sở')
INSERT [dbo].[phong_cach] ([id_phong_cach], [ma], [phong_cach]) VALUES (3, N'PC003', N'Phong Cách HipHop')
SET IDENTITY_INSERT [dbo].[phong_cach] OFF
GO
SET IDENTITY_INSERT [dbo].[san_pham] ON 

INSERT [dbo].[san_pham] ([id_san_pham], [id_co_ao], [id_khuy_ao], [id_kieu_dang], [id_duong_may], [id_hoa_tiet], [id_phong_cach], [id_chat_lieu], [id_mau_sac], [id_kich_thuoc], [id_tay_ao], [ten_san_pham], [anh_san_pham], [so_luong], [gia], [trang_thai], [ngay], [ma_san_pham], [mo_ta], [id_danh_muc]) VALUES (1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 2, N'Áo Phông Cổ Tròn Nữ', N'https://cf.shopee.vn/file/18e32978f46350820581cee0087eff52', 60, 750000, 1, CAST(N'2025-05-21T00:00:00.000' AS DateTime), N'SP01', N'Chất liệu cotton pha mềm mịn, co giãn tốt. Hình in sắc nét, không bong tróc sau nhiều lần giặt. Phù hợp cho các bạn học sinh, sinh viên yêu thích sự năng động và dễ thương.', 1)
INSERT [dbo].[san_pham] ([id_san_pham], [id_co_ao], [id_khuy_ao], [id_kieu_dang], [id_duong_may], [id_hoa_tiet], [id_phong_cach], [id_chat_lieu], [id_mau_sac], [id_kich_thuoc], [id_tay_ao], [ten_san_pham], [anh_san_pham], [so_luong], [gia], [trang_thai], [ngay], [ma_san_pham], [mo_ta], [id_danh_muc]) VALUES (2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, N'Áo Phông Nam Mùa Hè', N'https://dosi-in.com/images/detailed/42/CDL10_1.jpg', 30, 700000, 1, CAST(N'2025-05-21T00:00:00.000' AS DateTime), N'SP02', N'Thiết kế basic, dễ phối đồ, phù hợp mọi hoàn cảnh.', 2)
INSERT [dbo].[san_pham] ([id_san_pham], [id_co_ao], [id_khuy_ao], [id_kieu_dang], [id_duong_may], [id_hoa_tiet], [id_phong_cach], [id_chat_lieu], [id_mau_sac], [id_kich_thuoc], [id_tay_ao], [ten_san_pham], [anh_san_pham], [so_luong], [gia], [trang_thai], [ngay], [ma_san_pham], [mo_ta], [id_danh_muc]) VALUES (8, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, N'Áo Phông Đen', N'https://thuthuatnhanh.com/wp-content/uploads/2022/08/ao-thun-in-hinh-theo-yeu-cau.jpg', 40, 99000, 1, CAST(N'2025-05-27T00:00:00.000' AS DateTime), N'SP04', N'Kiểu dáng trẻ trung, năng động, thích hợp mặc hàng ngày.', 3)
INSERT [dbo].[san_pham] ([id_san_pham], [id_co_ao], [id_khuy_ao], [id_kieu_dang], [id_duong_may], [id_hoa_tiet], [id_phong_cach], [id_chat_lieu], [id_mau_sac], [id_kich_thuoc], [id_tay_ao], [ten_san_pham], [anh_san_pham], [so_luong], [gia], [trang_thai], [ngay], [ma_san_pham], [mo_ta], [id_danh_muc]) VALUES (10, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, N'Áo Phông Trắng', N'https://up.yimg.com/ib/th?id=OIP.EEsgoX1fu5h8wBmTKTi3wQHaHa&pid=Api&rs=1&c=1&qlt=95&w=121&h=121', 30, 99000, 1, CAST(N'2025-06-02T00:00:00.000' AS DateTime), N'SP05', N'Chất liệu cotton mềm mại, thấm hút mồ hôi tốt, thoải mái cho cả ngày dài. Thiết kế đơn giản, dễ phối đồ, phù hợp mặc đi chơi, đi học hay ở nhà. Form suông vừa vặn, tôn dáng tự nhiên.', 4)
INSERT [dbo].[san_pham] ([id_san_pham], [id_co_ao], [id_khuy_ao], [id_kieu_dang], [id_duong_may], [id_hoa_tiet], [id_phong_cach], [id_chat_lieu], [id_mau_sac], [id_kich_thuoc], [id_tay_ao], [ten_san_pham], [anh_san_pham], [so_luong], [gia], [trang_thai], [ngay], [ma_san_pham], [mo_ta], [id_danh_muc]) VALUES (11, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, N'Áo Phông Mùa Hè', N'https://up.yimg.com/ib/th?id=OIP.4p-2hXGUNLFMqTlR-wgcCwHaHa&pid=Api&rs=1&c=1&qlt=95&w=121&h=121', 50, 120000, 1, CAST(N'2025-06-02T00:00:00.000' AS DateTime), N'SP06', N'Phong cách trẻ trung, form rộng cá tính giúp che khuyết điểm cơ thể hiệu quả. Họa tiết in nổi bật trước ngực, thể hiện gu thời trang riêng. Chất vải co giãn nhẹ, thoáng mát.', 5)
INSERT [dbo].[san_pham] ([id_san_pham], [id_co_ao], [id_khuy_ao], [id_kieu_dang], [id_duong_may], [id_hoa_tiet], [id_phong_cach], [id_chat_lieu], [id_mau_sac], [id_kich_thuoc], [id_tay_ao], [ten_san_pham], [anh_san_pham], [so_luong], [gia], [trang_thai], [ngay], [ma_san_pham], [mo_ta], [id_danh_muc]) VALUES (12, 2, 1, 2, 1, 1, 1, 1, 2, 2, 2, N'Áo Phông Regular', N'https://tse1.mm.bing.net/th?id=OIP.cI0Mh-X9K8oNz84ylitqAgHaHa&pid=Api&P=0&h=180', 30, 200000, 1, CAST(N'2025-06-02T00:00:00.000' AS DateTime), N'SP07', N'Thiết kế tối ưu cho vận động, với chất liệu poly co giãn và thoáng khí cao. Phù hợp cho chạy bộ, tập gym, thể thao ngoài trời. Đường may chắc chắn, bền đẹp sau nhiều lần giặt.', 2)
INSERT [dbo].[san_pham] ([id_san_pham], [id_co_ao], [id_khuy_ao], [id_kieu_dang], [id_duong_may], [id_hoa_tiet], [id_phong_cach], [id_chat_lieu], [id_mau_sac], [id_kich_thuoc], [id_tay_ao], [ten_san_pham], [anh_san_pham], [so_luong], [gia], [trang_thai], [ngay], [ma_san_pham], [mo_ta], [id_danh_muc]) VALUES (14, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, N'áo phông nike', N'https://bizweb.dktcdn.net/100/331/646/products/dsc02966-optimized.jpg?v=1689999025307', 40, 200000, 1, CAST(N'2025-06-02T00:00:00.000' AS DateTime), N'SP08', N'Chất liệu cotton pha mềm mịn, co giãn tốt. Hình in sắc nét, không bong tróc sau nhiều lần giặt. Phù hợp cho các bạn học sinh, sinh viên yêu thích sự năng động và dễ thương.', 1)
INSERT [dbo].[san_pham] ([id_san_pham], [id_co_ao], [id_khuy_ao], [id_kieu_dang], [id_duong_may], [id_hoa_tiet], [id_phong_cach], [id_chat_lieu], [id_mau_sac], [id_kich_thuoc], [id_tay_ao], [ten_san_pham], [anh_san_pham], [so_luong], [gia], [trang_thai], [ngay], [ma_san_pham], [mo_ta], [id_danh_muc]) VALUES (17, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, N'Áo Phông Cổ Tròn Nữ', N'https://cf.shopee.vn/file/18e32978f46350820581cee0087eff52', 50, 20000, 1, CAST(N'2025-06-20T00:00:00.000' AS DateTime), N'SP10', N'Chất liệu cotton pha mềm mịn, co giãn tốt. Hình in sắc nét, không bong tróc sau nhiều lần giặt. Phù hợp cho các bạn học sinh, sinh viên yêu thích sự năng động và dễ thương.', 1)
INSERT [dbo].[san_pham] ([id_san_pham], [id_co_ao], [id_khuy_ao], [id_kieu_dang], [id_duong_may], [id_hoa_tiet], [id_phong_cach], [id_chat_lieu], [id_mau_sac], [id_kich_thuoc], [id_tay_ao], [ten_san_pham], [anh_san_pham], [so_luong], [gia], [trang_thai], [ngay], [ma_san_pham], [mo_ta], [id_danh_muc]) VALUES (18, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, N'Áo Phông Cổ Tròn Nữ', N'https://cf.shopee.vn/file/18e32978f46350820581cee0087eff52', 50, 200000, 1, CAST(N'2025-06-20T00:00:00.000' AS DateTime), N'SP11', N'Chất liệu cotton pha mềm mịn, co giãn tốt. Hình in sắc nét, không bong tróc sau nhiều lần giặt. Phù hợp cho các bạn học sinh, sinh viên yêu thích sự năng động và dễ thương.', 1)
SET IDENTITY_INSERT [dbo].[san_pham] OFF
GO
SET IDENTITY_INSERT [dbo].[tai_khoan] ON 

INSERT [dbo].[tai_khoan] ([id_tai_khoan], [id_nv], [id_kh], [ten_dang_nhap], [email], [loai_tai_khoan], [trang_thai], [mat_khau]) VALUES (1, NULL, NULL, N'phamvanc', N'phamvanc@example.com', 1, 1, N'a123456@')
INSERT [dbo].[tai_khoan] ([id_tai_khoan], [id_nv], [id_kh], [ten_dang_nhap], [email], [loai_tai_khoan], [trang_thai], [mat_khau]) VALUES (2, NULL, 1, N'nguyenvana', N'nguyenvana@example.com', 2, 1, N'b123')
INSERT [dbo].[tai_khoan] ([id_tai_khoan], [id_nv], [id_kh], [ten_dang_nhap], [email], [loai_tai_khoan], [trang_thai], [mat_khau]) VALUES (4, NULL, 4, N'baotranquoc123', N'bao2004tk@gmail.com', 2, 1, N'bao123')
SET IDENTITY_INSERT [dbo].[tai_khoan] OFF
GO
SET IDENTITY_INSERT [dbo].[tay_ao] ON 

INSERT [dbo].[tay_ao] ([id_tay_ao], [ma], [tay_ao]) VALUES (1, N'TA001', N'Tay dài')
INSERT [dbo].[tay_ao] ([id_tay_ao], [ma], [tay_ao]) VALUES (2, N'TA002', N'Tay ngắn')
INSERT [dbo].[tay_ao] ([id_tay_ao], [ma], [tay_ao]) VALUES (3, N'TA003', N'Tay áo rộng bó')
SET IDENTITY_INSERT [dbo].[tay_ao] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [uq_ma_san_pham]    Script Date: 6/27/2025 8:22:39 PM ******/
ALTER TABLE [dbo].[san_pham] ADD  CONSTRAINT [uq_ma_san_pham] UNIQUE NONCLUSTERED 
(
	[ma_san_pham] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[danh_muc] ADD  DEFAULT ('MADM001') FOR [ma_danh_muc]
GO
ALTER TABLE [dbo].[danh_muc] ADD  DEFAULT ((1)) FOR [trang_thai]
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD  CONSTRAINT [FK_hoa_don_giam_gia] FOREIGN KEY([id_gg])
REFERENCES [dbo].[giam_gia] ([id_gg])
GO
ALTER TABLE [dbo].[hoa_don] CHECK CONSTRAINT [FK_hoa_don_giam_gia]
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD  CONSTRAINT [FK_hoa_don_hinh_thuc] FOREIGN KEY([id_hinh_thuc])
REFERENCES [dbo].[hinh_thuc_thanh_toan] ([id_hinh_thuc])
GO
ALTER TABLE [dbo].[hoa_don] CHECK CONSTRAINT [FK_hoa_don_hinh_thuc]
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD  CONSTRAINT [FK_hoa_don_khach_hang] FOREIGN KEY([id_kh])
REFERENCES [dbo].[khach_hang] ([id_kh])
GO
ALTER TABLE [dbo].[hoa_don] CHECK CONSTRAINT [FK_hoa_don_khach_hang]
GO
ALTER TABLE [dbo].[hoa_don]  WITH CHECK ADD  CONSTRAINT [FK_hoa_don_nhan_vien] FOREIGN KEY([id_nv])
REFERENCES [dbo].[nhan_vien] ([id_nv])
GO
ALTER TABLE [dbo].[hoa_don] CHECK CONSTRAINT [FK_hoa_don_nhan_vien]
GO
ALTER TABLE [dbo].[hoa_don_chi_tiet]  WITH CHECK ADD  CONSTRAINT [FK_hdct_hoa_don] FOREIGN KEY([id_hoa_don])
REFERENCES [dbo].[hoa_don] ([id_hoa_don])
GO
ALTER TABLE [dbo].[hoa_don_chi_tiet] CHECK CONSTRAINT [FK_hdct_hoa_don]
GO
ALTER TABLE [dbo].[hoa_don_chi_tiet]  WITH CHECK ADD  CONSTRAINT [FK_hdct_san_pham] FOREIGN KEY([id_sp])
REFERENCES [dbo].[san_pham] ([id_san_pham])
GO
ALTER TABLE [dbo].[hoa_don_chi_tiet] CHECK CONSTRAINT [FK_hdct_san_pham]
GO
ALTER TABLE [dbo].[lich_su_hoa_don]  WITH CHECK ADD  CONSTRAINT [FK_lich_su_hoa_don] FOREIGN KEY([id_hoa_don])
REFERENCES [dbo].[hoa_don] ([id_hoa_don])
GO
ALTER TABLE [dbo].[lich_su_hoa_don] CHECK CONSTRAINT [FK_lich_su_hoa_don]
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD  CONSTRAINT [FK_san_pham_chat_lieu] FOREIGN KEY([id_chat_lieu])
REFERENCES [dbo].[chat_lieu] ([id_chat_lieu])
GO
ALTER TABLE [dbo].[san_pham] CHECK CONSTRAINT [FK_san_pham_chat_lieu]
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD  CONSTRAINT [FK_san_pham_co_ao] FOREIGN KEY([id_co_ao])
REFERENCES [dbo].[co_ao] ([id_co_ao])
GO
ALTER TABLE [dbo].[san_pham] CHECK CONSTRAINT [FK_san_pham_co_ao]
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD  CONSTRAINT [FK_san_pham_duong_may] FOREIGN KEY([id_duong_may])
REFERENCES [dbo].[duong_may] ([id_duong_may])
GO
ALTER TABLE [dbo].[san_pham] CHECK CONSTRAINT [FK_san_pham_duong_may]
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD  CONSTRAINT [FK_san_pham_hoa_tiet] FOREIGN KEY([id_hoa_tiet])
REFERENCES [dbo].[hoa_tiet] ([id_hoa_tiet])
GO
ALTER TABLE [dbo].[san_pham] CHECK CONSTRAINT [FK_san_pham_hoa_tiet]
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD  CONSTRAINT [FK_san_pham_khuy_ao] FOREIGN KEY([id_khuy_ao])
REFERENCES [dbo].[khuy_ao] ([id_khuy_ao])
GO
ALTER TABLE [dbo].[san_pham] CHECK CONSTRAINT [FK_san_pham_khuy_ao]
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD  CONSTRAINT [FK_san_pham_kich_thuoc] FOREIGN KEY([id_kich_thuoc])
REFERENCES [dbo].[kich_thuoc] ([id_kich_thuoc])
GO
ALTER TABLE [dbo].[san_pham] CHECK CONSTRAINT [FK_san_pham_kich_thuoc]
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD  CONSTRAINT [FK_san_pham_kieu_dang] FOREIGN KEY([id_kieu_dang])
REFERENCES [dbo].[kieu_dang] ([id_kieu_dang])
GO
ALTER TABLE [dbo].[san_pham] CHECK CONSTRAINT [FK_san_pham_kieu_dang]
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD  CONSTRAINT [FK_san_pham_mau_sac] FOREIGN KEY([id_mau_sac])
REFERENCES [dbo].[mau_sac] ([id_mau_sac])
GO
ALTER TABLE [dbo].[san_pham] CHECK CONSTRAINT [FK_san_pham_mau_sac]
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD  CONSTRAINT [FK_san_pham_phong_cach] FOREIGN KEY([id_phong_cach])
REFERENCES [dbo].[phong_cach] ([id_phong_cach])
GO
ALTER TABLE [dbo].[san_pham] CHECK CONSTRAINT [FK_san_pham_phong_cach]
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD  CONSTRAINT [FK_san_pham_tay_ao] FOREIGN KEY([id_tay_ao])
REFERENCES [dbo].[tay_ao] ([id_tay_ao])
GO
ALTER TABLE [dbo].[san_pham] CHECK CONSTRAINT [FK_san_pham_tay_ao]
GO
ALTER TABLE [dbo].[san_pham]  WITH CHECK ADD  CONSTRAINT [FK_SanPham_DanhMuc] FOREIGN KEY([id_danh_muc])
REFERENCES [dbo].[danh_muc] ([id_danh_muc])
GO
ALTER TABLE [dbo].[san_pham] CHECK CONSTRAINT [FK_SanPham_DanhMuc]
GO
ALTER TABLE [dbo].[tai_khoan]  WITH CHECK ADD  CONSTRAINT [FK_tai_khoan_khach_hang] FOREIGN KEY([id_kh])
REFERENCES [dbo].[khach_hang] ([id_kh])
GO
ALTER TABLE [dbo].[tai_khoan] CHECK CONSTRAINT [FK_tai_khoan_khach_hang]
GO
ALTER TABLE [dbo].[tai_khoan]  WITH CHECK ADD  CONSTRAINT [FK_tai_khoan_nhan_vien] FOREIGN KEY([id_nv])
REFERENCES [dbo].[nhan_vien] ([id_nv])
GO
ALTER TABLE [dbo].[tai_khoan] CHECK CONSTRAINT [FK_tai_khoan_nhan_vien]
GO
